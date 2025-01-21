/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @run testng/othervm
 *     --enable-native-access=ALL-UNNAMED
 *     TestNULLAddress
 */

import org.testng.annotations.Test;

import java.lang.foreign.Linker;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

import static org.testng.Assert.*;

public class TestNULLAddress {

    static {
        System.loadLibrary("Null");
    }

    static final Linker LINKER = Linker.nativeLinker();

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNULLLinking() {
        LINKER.downcallHandle(
                MemorySegment.NULL,
                FunctionDescriptor.ofVoid());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNULLVirtual() throws Throwable {
        MethodHandle mh = LINKER.downcallHandle(
                FunctionDescriptor.ofVoid());
        mh.invokeExact(MemorySegment.NULL);
    }

    @Test
    public void testNULLReturn_target() throws Throwable {
        MethodHandle mh = LINKER.downcallHandle(SymbolLookup.loaderLookup().find("get_null").get(),
                FunctionDescriptor.of(ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_INT)));
        MemorySegment ret = (MemorySegment)mh.invokeExact();
        assertTrue(ret.equals(MemorySegment.NULL));
    }

    @Test
    public void testNULLReturn_plain() throws Throwable {
        MethodHandle mh = LINKER.downcallHandle(SymbolLookup.loaderLookup().find("get_null").get(),
                FunctionDescriptor.of(ValueLayout.ADDRESS));
        MemorySegment ret = (MemorySegment)mh.invokeExact();
        assertTrue(ret.equals(MemorySegment.NULL));
    }
}
