/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8026844
 * @summary Test repeating subtractExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.SubExactIRepeatTest
 */

package compiler.intrinsics.mathexact;

import jdk.test.lib.Utils;

import java.util.Random;

public class SubExactIRepeatTest {
    public static void main(String[] args) {
        runTest(new Verify.SubExactI());
    }

    public static int nonExact(int x, int y, Verify.BinaryMethod method) {
        int result = method.unchecked(x, y);
        result += method.unchecked(x, y);
        result += method.unchecked(x, y);
        result += method.unchecked(x, y);
        return result;
    }

    public static void runTest(Verify.BinaryMethod method) {
        Random rnd = Utils.getRandomInstance();
        for (int i = 0; i < 50000; ++i) {
            int x = Integer.MIN_VALUE + 10;
            int y = Integer.MAX_VALUE - 10 + rnd.nextInt(5);

            int c = rnd.nextInt() / 2;
            int d = rnd.nextInt() / 2;

            int a = catchingExact(x, y, method);

            if (a != 36) {
                throw new RuntimeException("a != 36 : " + a);
            }

            int b = nonExact(c, d, method);
            int n = exact(c, d, method);


            if (n != b) {
                throw new RuntimeException("n != b : " + n + " != " + b);
            }
        }
    }

    public static int exact(int x, int y, Verify.BinaryMethod method) {
        int result = 0;
        result += method.checkMethod(x, y);
        result += method.checkMethod(x, y);
        result += method.checkMethod(x, y);
        result += method.checkMethod(x, y);
        return result;
    }

    public static int catchingExact(int x, int y, Verify.BinaryMethod method) {
        int result = 0;
        try {
            result += 5;
            result = method.checkMethod(x, y);
        } catch (ArithmeticException e) {
            result += 1;
        }
        try {
            result += 6;

            result += method.checkMethod(x, y);
        } catch (ArithmeticException e) {
            result += 2;
        }
        try {
            result += 7;
            result += method.checkMethod(x, y);
        } catch (ArithmeticException e) {
            result += 3;
        }
        try {
            result += 8;
            result += method.checkMethod(x, y);
        } catch (ArithmeticException e) {
            result += 4;
        }
        return result;
    }
}
