/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8161720
 * @modules java.base/jdk.internal.misc
 * @run main/othervm -Xint UnsafeOnHeapBooleanTest 1
 * @run main/othervm -XX:-UseOnStackReplacement -XX:+TieredCompilation -XX:TieredStopAtLevel=3 -Xbatch UnsafeOnHeapBooleanTest 20000
 * @run main/othervm -XX:-UseOnStackReplacement -XX:-TieredCompilation -Xbatch UnsafeOnHeapBooleanTest 20000
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeOnHeapBooleanTest {
    static short static_v;
    static boolean bool0 = false, bool1 = false, result = false;
    static Unsafe UNSAFE = Unsafe.getUnsafe();

    public static void test() {
        try {
            // Write two bytes into the static field
            // UnsafeOnHeapBooleanTest.static_v write two values. Both
            // bytes correspond to the boolean value 'true'.
            Field staticVField = UnsafeOnHeapBooleanTest.class.getDeclaredField("static_v");
            Object base = UNSAFE.staticFieldBase(staticVField);
            long offset = UNSAFE.staticFieldOffset(staticVField);
            UNSAFE.putShort(base, offset, (short)0x0204);

            // Read two bytes from the static field
            // UnsafeOnHeapBooleanTest.static_v (as booleans).
            bool0 = UNSAFE.getBoolean(base, offset + 0);
            bool1 = UNSAFE.getBoolean(base, offset + 1);
            result = bool0 & bool1;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("### Test failure: static field UnsafeOnHeapBooleanTest.static_v was not found");
        }
    }

    public static void main(String args[]) {
        System.out.println("### Test started");

        if (args.length != 1) {
            throw new RuntimeException("### Test failure: test called with incorrect number of arguments");
        }

        try {
            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                test();
            }

            // Check if the two 'true' boolean values were normalized
            // (i.e., reduced from the range 1...255 to 1).
            if (!bool0 || !bool1 || !result) {
                System.out.println("Some of the results below are wrong");
                System.out.println("bool0 is: " + bool0);
                System.out.println("bool1 is: " + bool1);
                System.out.println("bool0 & bool1 is: " + result);
                System.out.println("===================================");
                throw new RuntimeException("### Test failed");
            } else {
                System.out.println("Test generated correct results");
                System.out.println("bool0 is: " + bool0);
                System.out.println("bool1 is: " + bool1);
                System.out.println("bool0 & bool1 is: " + result);
                System.out.println("===================================");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("### Test failure: test called with incorrectly formatted parameter");
        }

        System.out.println("### Test passed");
    }
}
