/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8275330
 * @summary C2: assert(n->is_Root() || n->is_Region() || n->is_Phi() || n->is_MachMerge() || def_block->dominates(block)) failed: uses must be dominated by definitions
 *
 * @run main/othervm -Xmx512m -XX:+UnlockDiagnosticVMOptions -Xcomp -XX:CompileOnly=TestDeadPostLoopBecausePredicate::* TestDeadPostLoopBecausePredicate
 *
 */


public class TestDeadPostLoopBecausePredicate {

    public static final int N = 400;

    public static int iFld=54270;
    public static int iFld1=-4;
    public int iFld2=201;

    public int mainTest(String[] strArr1) {

        int i=0, i17=8052, i19=22380, i20=60894, iArr[]=new int[N];
        init(iArr, 4);

        i = 1;
        do {
            for (i17 = 5; i17 < 114; i17++) {
                switch ((i17 % 7) + 126) {
                case 126:
                    for (i19 = 2; i19 > i; i19 -= 3) {
                        try {
                            i20 = (iFld2 % TestDeadPostLoopBecausePredicate.iFld1);
                            i20 = (iArr[i19 - 1] % TestDeadPostLoopBecausePredicate.iFld);
                            TestDeadPostLoopBecausePredicate.iFld = (TestDeadPostLoopBecausePredicate.iFld1 % iArr[i19]);
                        } catch (ArithmeticException a_e) {}
                    }
                    break;
                }
            }
        } while (++i < 220);

        return i20;
    }

    public static void init(int[] a, int seed) {
        for (int j = 0; j < a.length; j++) {
            a[j] = (j % 2 == 0) ? seed + j : seed - j;
        }
    }

    public static void main(String[] strArr) {
        TestDeadPostLoopBecausePredicate _instance = new TestDeadPostLoopBecausePredicate();
        for (int i = 0; i < 10; i++ ) {
            _instance.mainTest(strArr);
        }
    }
}
