/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Verify behaviour of Unsafe.get/putFloat
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main GetPutFloat
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;
import static jdk.test.lib.Asserts.*;

public class GetPutFloat {
    public static void main(String args[]) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        Test t = new Test();
        Field field = Test.class.getField("f");

        long offset = unsafe.objectFieldOffset(field);
        assertEquals(-1.0f, unsafe.getFloat(t, offset));
        unsafe.putFloat(t, offset, 0.0f);
        assertEquals(0.0f, unsafe.getFloat(t, offset));

        long address = unsafe.allocateMemory(8);
        unsafe.putFloat(address, 1.0f);
        assertEquals(1.0f, unsafe.getFloat(address));
        unsafe.freeMemory(address);

        float arrayFloat[] = { -1.0f, 0.0f, 1.0f, 2.0f };
        int scale = unsafe.arrayIndexScale(arrayFloat.getClass());
        offset = unsafe.arrayBaseOffset(arrayFloat.getClass());
        for (int i = 0; i < arrayFloat.length; i++) {
            assertEquals(unsafe.getFloat(arrayFloat, offset), arrayFloat[i]);
            offset += scale;
        }
    }

    static class Test {
        public float f = -1.0f;
    }
}
