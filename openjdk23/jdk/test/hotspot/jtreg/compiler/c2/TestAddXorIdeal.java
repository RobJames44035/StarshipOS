/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8273021
 * @summary C2: Improve Add and Xor ideal optimizations
 * @library /test/lib
 * @run main/othervm -XX:-TieredCompilation
 *                   -XX:CompileCommand=dontinline,compiler.c2.TestAddXorIdeal::test*
 *                   compiler.c2.TestAddXorIdeal
 */
package compiler.c2;

import java.util.Random;

import jdk.test.lib.Asserts;
import jdk.test.lib.Utils;

public class TestAddXorIdeal {

    public static int test1(int x) {
        return ~x + 1;
    }

    public static int test2(int x) {
        return ~(x - 1);
    }

    public static long test3(long x) {
        return ~x + 1L;
    }

    public static long test4(long x) {
        return ~(x - 1L);
    }

    public static int test5(int x) {
        return 1 + ~x;
    }

    public static int test6(int x) {
        return ~(-1 + x);
    }

    public static long test7(long x) {
        return 1L + ~x;
    }

    public static long test8(long x) {
        return ~(-1L + x);
    }

    public static void main(String... args) {
        Random random = Utils.getRandomInstance();
        for (int i = 0; i < 50_000; i++) {
            int a = random.nextInt();
            long b = random.nextLong();
            Asserts.assertTrue(test1(a) == -a);
            Asserts.assertTrue(test2(a) == -a);
            Asserts.assertTrue(test3(b) == -b);
            Asserts.assertTrue(test4(b) == -b);
            Asserts.assertTrue(test5(a) == -a);
            Asserts.assertTrue(test6(a) ==  -a);
            Asserts.assertTrue(test7(b) == -b);
            Asserts.assertTrue(test8(b) == -b);
        }
    }
}