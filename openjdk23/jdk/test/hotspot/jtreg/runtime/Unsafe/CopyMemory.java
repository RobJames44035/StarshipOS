/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Verifies behaviour of Unsafe.copyMemory
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main CopyMemory
 */

import jdk.internal.misc.Unsafe;
import static jdk.test.lib.Asserts.*;

public class CopyMemory {
    final static int LENGTH = 8;
    public static void main(String args[]) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        long src = unsafe.allocateMemory(LENGTH);
        long dst = unsafe.allocateMemory(LENGTH);
        assertNotEquals(src, 0L);
        assertNotEquals(dst, 0L);

        // call copyMemory() with different lengths and verify the contents of
        // the destination array
        for (int i = 0; i < LENGTH; i++) {
            unsafe.putByte(src + i, (byte)i);
            unsafe.copyMemory(src, dst, i);
            for (int j = 0; j < i; j++) {
                assertEquals(unsafe.getByte(src + j), unsafe.getByte(src + j));
            }
        }
        unsafe.freeMemory(src);
        unsafe.freeMemory(dst);
    }
}
