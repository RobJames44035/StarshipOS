/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test id=default_gc
 * @bug 8277602
 * @library /test/lib
 * @library ../
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 *
 * @run main/othervm
 *   -Xbootclasspath/a:.
 *   -XX:+UnlockDiagnosticVMOptions
 *   -XX:+WhiteBoxAPI
 *   --enable-native-access=ALL-UNNAMED
 *   -Xbatch
 *   TestUpcallDeopt
 */

import java.lang.foreign.Arena;
import java.lang.foreign.Linker;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.ref.Reference;

import jdk.test.whitebox.WhiteBox;

import static java.lang.invoke.MethodHandles.lookup;

public class TestUpcallDeopt extends NativeTestHelper {
    static final WhiteBox WB = WhiteBox.getWhiteBox();

    static final Linker linker = Linker.nativeLinker();

    static final MethodHandle MH_foo;
    static final MethodHandle MH_m;

    static {
        try {
            System.loadLibrary("UpcallDeopt");
            MH_foo = linker.downcallHandle(
                    findNativeOrThrow("foo"),
                    FunctionDescriptor.ofVoid(C_POINTER, C_INT, C_INT, C_INT, C_INT));
            MH_m = lookup().findStatic(TestUpcallDeopt.class, "m",
                    MethodType.methodType(void.class, int.class, int.class, int.class, int.class));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean armed;

    // we need to deoptimize through an uncommon trap in the callee of the optimized upcall stub
    // that is created when calling upcallStub below
    public static void main(String[] args) throws Throwable {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment stub = linker.upcallStub(MH_m, FunctionDescriptor.ofVoid(C_INT, C_INT, C_INT, C_INT), arena);
            armed = false;
            for (int i = 0; i < 20_000; i++) {
                payload(stub); // warmup
            }

            armed = true;
            payload(stub); // test
        }
    }

    static void payload(MemorySegment cb) throws Throwable {
        MH_foo.invokeExact(cb, 0, 1, 2, 3);
        Reference.reachabilityFence(cb); // keep oop alive across call
    }

    // Takes a bunch of arguments, even though unused, to test
    // if the caller's frame is extended enough to spill these arguments.
    static void m(int a0, int a1, int a2, int a3) {
        if (armed) {
            // Trigger uncommon trap from this frame
            WB.verifyFrames(/*log=*/true, /*updateRegisterMap=*/true);
            WB.verifyFrames(/*log=*/true, /*updateRegisterMap=*/false); // triggers different code paths
        }
    }

}
