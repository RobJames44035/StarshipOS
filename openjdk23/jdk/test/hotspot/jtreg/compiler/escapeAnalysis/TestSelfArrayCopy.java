/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8229016 8231055
 * @summary Test correct elimination of array allocation with arraycopy to itself.
 * @library /test/lib
 * @run main/othervm -Xbatch -XX:CompileCommand=compileonly,compiler.escapeAnalysis.TestSelfArrayCopy::test*
 *                   compiler.escapeAnalysis.TestSelfArrayCopy
 */

package compiler.escapeAnalysis;

import jdk.test.lib.Utils;

public class TestSelfArrayCopy {
    private static boolean b = false;
    private static final int rI1 = Utils.getRandomInstance().nextInt();
    private static final int rI2 = Utils.getRandomInstance().nextInt();

    private static int test1() {
        // Non-escaping allocation
        Integer[] array = {rI1, rI2};
        // Arraycopy with src == dst
        System.arraycopy(array, 0, array, 0, array.length - 1);
        if (b) {
            // Uncommon trap
            System.out.println(array[0]);
        }
        return array[0] + array[1];
    }

    private static int test2() {
        // Non-escaping allocation
        Integer[] array = {rI1, rI2};
        // Arraycopy with src == dst
        System.arraycopy(array, 0, array, 1, 1);
        if (b) {
            // Uncommon trap
            System.out.println(array[0]);
        }
        return array[0] + array[1];
    }

    public static void main(String[] args) {
        int expected1 = rI1 + rI2;
        int expected2 = rI1 + rI1;
        // Trigger compilation
        for (int i = 0; i < 20_000; ++i) {
            int result = test1();
            if (result != expected1) {
                throw new RuntimeException("Incorrect result: " + result + " != " + expected1);
            }
            result = test2();
            if (result != expected2) {
                throw new RuntimeException("Incorrect result: " + result + " != " + expected2);
            }
        }
        b = true;
        int result = test1();
        if (result != expected1) {
            throw new RuntimeException("Incorrect result: " + result + " != " + expected1);
        }
        result = test2();
        if (result != expected2) {
            throw new RuntimeException("Incorrect result: " + result + " != " + expected2);
        }
    }
}
