/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Verify behaviour of Unsafe.get/putLong
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main GetPutLong
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;
import static jdk.test.lib.Asserts.*;

public class GetPutLong {
    public static void main(String args[]) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        Test t = new Test();
        Field field = Test.class.getField("l");

        long offset = unsafe.objectFieldOffset(field);
        assertEquals(-1L, unsafe.getLong(t, offset));
        unsafe.putLong(t, offset, 0L);
        assertEquals(0L, unsafe.getLong(t, offset));

        long address = unsafe.allocateMemory(8);
        unsafe.putLong(address, 1L);
        assertEquals(1L, unsafe.getLong(address));
        unsafe.freeMemory(address);

        long arrayLong[] = { -1, 0, 1, 2 };
        int scale = unsafe.arrayIndexScale(arrayLong.getClass());
        offset = unsafe.arrayBaseOffset(arrayLong.getClass());
        for (int i = 0; i < arrayLong.length; i++) {
            assertEquals(unsafe.getLong(arrayLong, offset), arrayLong[i]);
            offset += scale;
        }
    }

    static class Test {
        public long l = -1L;
    }
}
