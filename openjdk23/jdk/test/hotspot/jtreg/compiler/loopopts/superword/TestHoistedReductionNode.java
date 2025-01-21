/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8286177
 * @summary Test that inconsistent reduction node-loop state does not trigger
 *          assertion failures when the inconsistency does not lead to a
 *          miscompilation.
 * @run main/othervm -Xbatch compiler.loopopts.superword.TestHoistedReductionNode
 */
package compiler.loopopts.superword;

public class TestHoistedReductionNode {

    static boolean b = true;

    static int test() {
        int acc = 0;
        int i = 0;
        do {
            int j = 0;
            do {
                if (b) {
                    acc += j;
                }
                j++;
            } while (j < 5);
            i++;
        } while (i < 100);
        return acc;

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10_000; i++) {
            test();
        }
    }
}
