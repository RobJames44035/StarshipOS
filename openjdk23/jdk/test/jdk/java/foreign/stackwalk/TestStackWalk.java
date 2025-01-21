/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test id=default_gc
 * @requires vm.gc != "Z"
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
 *   TestStackWalk
 */

/*
 * @test id=Z
 * @requires vm.gc.Z
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
 *   -XX:+UseZGC
 *   TestStackWalk
 */

/*
 * @test id=shenandoah
 * @requires vm.gc.Shenandoah
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
 *   -XX:+UseShenandoahGC
 *   TestStackWalk
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

public class TestStackWalk extends NativeTestHelper {
    static final WhiteBox WB = WhiteBox.getWhiteBox();

    static final Linker linker = Linker.nativeLinker();

    static final MethodHandle MH_foo;
    static final MethodHandle MH_m;

    static {
        try {
            System.loadLibrary("StackWalk");
            MH_foo = linker.downcallHandle(
                    findNativeOrThrow("foo"),
                    FunctionDescriptor.ofVoid(C_POINTER));
            MH_m = lookup().findStatic(TestStackWalk.class, "m", MethodType.methodType(void.class));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean armed;

    public static void main(String[] args) throws Throwable {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment stub = linker.upcallStub(MH_m, FunctionDescriptor.ofVoid(), arena);
            armed = false;
            for (int i = 0; i < 20_000; i++) {
                payload(stub); // warmup
            }

            armed = true;
            payload(stub); // test
        }
    }

    static void payload(MemorySegment cb) throws Throwable {
        MH_foo.invoke(cb);
        Reference.reachabilityFence(cb); // keep oop alive across call
    }

    static void m() {
        if (armed) {
            WB.verifyFrames(/*log=*/true, /*updateRegisterMap=*/true);
            WB.verifyFrames(/*log=*/true, /*updateRegisterMap=*/false); // triggers different code paths
        }
    }

}
