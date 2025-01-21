/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8223923
 * @modules java.base/jdk.internal.misc
 * @run main/othervm -Xbatch compiler.unsafe.MismatchedUnsafeAccessTest
 */
package compiler.unsafe;

import jdk.internal.misc.Unsafe;

public class MismatchedUnsafeAccessTest {
    private static final Unsafe UNSAFE = Unsafe.getUnsafe();

    public static class Test {
        public int x = 0;
        public int y = 0;

        public static final long offsetX;
        public static final long offsetY;

        static {
            try {
                offsetX = UNSAFE.objectFieldOffset(Test.class.getField("x"));
                offsetY = UNSAFE.objectFieldOffset(Test.class.getField("y"));
            } catch (NoSuchFieldException e) {
                throw new InternalError(e);
            }
            // Validate offsets
            if (offsetX >= offsetY || offsetY - offsetX != 4) {
                throw new InternalError("Wrong offsets: " + offsetX + " " + offsetY);
            }
        }
    }

    public static int test(long l) {
        Test a = new Test();
        UNSAFE.putLong(a, Test.offsetX, l); // mismatched access; interferes with subsequent load
        return UNSAFE.getInt(a, Test.offsetY);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            int res = test(-1L);
            if (res != -1) {
                throw new AssertionError("Wrong result: " + res);
            }
        }
        System.out.println("TEST PASSED");
    }
}
