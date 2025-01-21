/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8187822
 * @summary C2 conditonal move optimization might create broken graph
 * @requires vm.flavor == "server"
 * @run main/othervm -XX:-UseOnStackReplacement -XX:-BackgroundCompilation -XX:CompileCommand=dontinline,TestCMovSplitThruPhi::not_inlined -XX:CompileOnly=TestCMovSplitThruPhi::test -XX:-LoopUnswitching TestCMovSplitThruPhi
 *
 */

public class TestCMovSplitThruPhi {
    static int f;

    static int test(boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
        int v3 = 0;
        if (flag4) {
            for (int i = 0; i < 10; i++) {
                int v1 = 0;
                if (flag1) {
                    v1 = not_inlined();
                }
                // AddI below will be candidate for split through Phi
                int v2 = v1;
                if (flag2) {
                    v2 = f + v1;
                }
                // test above will be converted to CMovI
                if (flag3) {
                    v3 = v2 * 2;
                    break;
                }
            }
        }
        return v3;
    }

    private static int not_inlined() {
        return 0;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20000; i++) {
            test((i % 2) == 0, (i % 2) == 0, (i % 100) == 1, (i % 1000) == 1);
        }
    }
}
