/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @library ../
 * @run testng/othervm
 *   --enable-native-access=ALL-UNNAMED
 *   TestVirtualCalls
 */

import java.lang.foreign.Linker;
import java.lang.foreign.FunctionDescriptor;

import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;

public class TestVirtualCalls extends NativeTestHelper {

    static final Linker abi = Linker.nativeLinker();

    static final MethodHandle func;
    static final MemorySegment funcA;
    static final MemorySegment funcB;
    static final MemorySegment funcC;

    static {
        func = abi.downcallHandle(
                FunctionDescriptor.of(C_INT));

        System.loadLibrary("Virtual");
        funcA = findNativeOrThrow("funcA");
        funcB = findNativeOrThrow("funcB");
        funcC = findNativeOrThrow("funcC");
    }

    @Test
    public void testVirtualCalls() throws Throwable {
        assertEquals((int) func.invokeExact(funcA), 1);
        assertEquals((int) func.invokeExact(funcB), 2);
        assertEquals((int) func.invokeExact(funcC), 3);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullTarget() throws Throwable {
        int x = (int) func.invokeExact((MemorySegment)null);
    }

}
