/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

// Note: we run the test with MallocLimit for the "other" category set to 100m (oom mode),
// in order to trigger and observe a fake os::malloc oom. This needs NMT.

/*
 * @test
 * @requires vm.compMode != "Xcomp"
 * @bug 8058897
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary -XX:MallocLimit=other:100m:oom Reallocate
 */

import jdk.internal.misc.Unsafe;
import static jdk.test.lib.Asserts.*;

public class Reallocate {
    public static void main(String args[]) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        long address = unsafe.allocateMemory(1);
        assertNotEquals(address, 0L);

        // Make sure we reallocate correctly
        unsafe.putByte(address, Byte.MAX_VALUE);
        address = unsafe.reallocateMemory(address, 2);
        assertNotEquals(address, 0L);
        assertEquals(unsafe.getByte(address), Byte.MAX_VALUE);

        // Reallocating with a 0 size should return a null pointer
        address = unsafe.reallocateMemory(address, 0);
        assertEquals(address, 0L);

        // Reallocating with a null pointer should result in a normal allocation
        address = unsafe.reallocateMemory(0L, 1);
        assertNotEquals(address, 0L);
        unsafe.putByte(address, Byte.MAX_VALUE);
        assertEquals(unsafe.getByte(address), Byte.MAX_VALUE);

        // Make sure we can throw an OOME when we fail to reallocate due to OOM
        try {
            unsafe.reallocateMemory(address, 100 * 1024 * 1024);
        } catch (OutOfMemoryError e) {
            // Expected
            return;
        }
        throw new RuntimeException("Did not get expected OOM");
    }
}
