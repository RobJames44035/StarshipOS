/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Verifies the behaviour of Unsafe.fieldOffset
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main FieldOffset
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;
import java.lang.reflect.*;
import static jdk.test.lib.Asserts.*;

public class FieldOffset {
    public static void main(String args[]) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        Field[] fields = Test.class.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            long offset = unsafe.objectFieldOffset(fields[i]);
            // Ensure we got a valid offset value back
            assertNotEquals(offset, unsafe.INVALID_FIELD_OFFSET);

            // Make sure the field offset is unique
            for (int j = 0; j < i; j++) {
                assertNotEquals(offset, unsafe.objectFieldOffset(fields[j]));
            }
        }

        fields = StaticTest.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            long offset = unsafe.staticFieldOffset(fields[i]);
            // Ensure we got a valid offset value back
            assertNotEquals(offset, unsafe.INVALID_FIELD_OFFSET);

            // Make sure the field offset is unique
            for (int j = 0; j < i; j++) {
                assertNotEquals(offset, unsafe.staticFieldOffset(fields[j]));
            }
        }

    }

    class Test {
        boolean booleanField;
        byte byteField;
        char charField;
        double doubleField;
        float floatField;
        int intField;
        long longField;
        Object objectField;
        short shortField;
    }

    static class StaticTest {
        static boolean booleanField;
        static byte byteField;
        static char charField;
        static double doubleField;
        static float floatField;
        static int intField;
        static long longField;
        static Object objectField;
        static short shortField;
    }

}
