/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Verify behaviour of Unsafe.get/putChar
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main GetPutChar
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;
import static jdk.test.lib.Asserts.*;

public class GetPutChar {
    public static void main(String args[]) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        Test t = new Test();
        Field field = Test.class.getField("c");

        long offset = unsafe.objectFieldOffset(field);
        assertEquals('\u0000', unsafe.getChar(t, offset));
        unsafe.putChar(t, offset, '\u0001');
        assertEquals('\u0001', unsafe.getChar(t, offset));

        long address = unsafe.allocateMemory(8);
        unsafe.putChar(address, '\u0002');
        assertEquals('\u0002', unsafe.getChar(address));
        unsafe.freeMemory(address);

        char arrayChar[] = { '\uabcd', '\u00ff', '\uff00', };
        int scale = unsafe.arrayIndexScale(arrayChar.getClass());
        offset = unsafe.arrayBaseOffset(arrayChar.getClass());
        for (int i = 0; i < arrayChar.length; i++) {
            assertEquals(unsafe.getChar(arrayChar, offset), arrayChar[i]);
            offset += scale;
        }
    }

    static class Test {
        public char c = '\u0000';
    }
}
