/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8309266
 * @summary Integer overflow in LoopLimit::Value during PhaseCCP::analyze, triggered by the Phi Node from "flag ? Integer.MAX_VALUE : 1000"
 * @run main/othervm -Xbatch -XX:CompileOnly=compiler.loopopts.TestLoopLimitOverflowDuringCCP::* compiler.loopopts.TestLoopLimitOverflowDuringCCP
 */

package compiler.loopopts;

public class TestLoopLimitOverflowDuringCCP {
    static boolean flag;

    public static void main(String[] strArr) {
        for (int i = 0; i < 10000; i++) {
            flag = !flag;
            test();
        }
    }

    public static void test() {
        int limit = flag ? Integer.MAX_VALUE : 1000;
        int i = 0;
        while (i < limit) {
            i += 3;
            if (flag) {
                return;
            }
        }
    }
}