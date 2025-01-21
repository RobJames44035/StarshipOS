/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8287284
 * @summary The phi of cnt is split from the inner to the outer loop,
 *          and then from outer loop to the inner loop again.
 *          This ended in a endless optimization cycle.
 * @library /test/lib /
 * @run driver compiler.c2.loopopts.TestSplitThruPhiInfinitely
 */

package compiler.c2.loopopts;

import compiler.lib.ir_framework.*;

public class TestSplitThruPhiInfinitely {

    public static int cnt = 1;

    @Test
    @IR(counts = {IRNode.PHI, " <= 10"})
    public static void test() {
        int j = 0;
        do {
            j = cnt;
            for (int k = 0; k < 20000; k++) {
                cnt += 2;
            }
        } while (++j < 10);
    }

    public static void main(String[] args) {
        TestFramework.runWithFlags("-XX:-PartialPeelLoop");
    }
}
