/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @bug 8220374 8241492
 * @summary C2: LoopStripMining doesn't strip as expected
 * @requires vm.compiler2.enabled
 *
 * @library /test/lib
 * @run driver compiler.loopstripmining.CheckLoopStripMining
 */

package compiler.loopstripmining;

import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;

public class CheckLoopStripMining {
    public static void main(String args[]) throws Exception {
        ProcessTools.executeTestJava("-XX:+UnlockDiagnosticVMOptions",
                                    "-XX:+SafepointTimeout",
                                    "-XX:+SafepointALot",
                                    "-XX:+AbortVMOnSafepointTimeout",
                                    "-XX:SafepointTimeoutDelay=" + Utils.adjustTimeout(300),
                                    "-XX:GuaranteedSafepointInterval=" + Utils.adjustTimeout(300),
                                    "-XX:-TieredCompilation",
                                    "-XX:+UseCountedLoopSafepoints",
                                    "-XX:LoopStripMiningIter=1000",
                                    "-XX:LoopUnrollLimit=0",
                                    "-XX:CompileCommand=compileonly,compiler.loopstripmining.CheckLoopStripMining$Test1::test_loop",
                                    "-Xcomp",
                                    Test1.class.getName())
            .shouldHaveExitValue(0)
            .stdoutShouldContain("sum: 715827882");

        ProcessTools.executeTestJava("-XX:+UnlockDiagnosticVMOptions",
                                    "-XX:+SafepointTimeout",
                                    "-XX:+SafepointALot",
                                    "-XX:+AbortVMOnSafepointTimeout",
                                    "-XX:SafepointTimeoutDelay=" + Utils.adjustTimeout(300),
                                    "-XX:GuaranteedSafepointInterval=" + Utils.adjustTimeout(300),
                                    "-XX:-TieredCompilation",
                                    "-XX:+UseCountedLoopSafepoints",
                                    "-XX:LoopStripMiningIter=1000",
                                    "-XX:LoopUnrollLimit=0",
                                    "-XX:-BackgroundCompilation",
                                    Test2.class.getName())
            .shouldHaveExitValue(0);
    }

    public static class Test1 {
        public static int test_loop(int x) {
            int sum = 0;
            if (x != 0) {
                for (int y = 1; y < Integer.MAX_VALUE; ++y) {
                    if (y % x == 0) ++sum;
                }
            }
            return sum;
        }

        public static void main(String args[]) {
            int sum = test_loop(3);
            System.out.println("sum: " + sum);
        }
    }

    public static class Test2 {
        public static int test_loop(int start, int stop) {
            int sum = 0;
            for (int x = start; x < stop; x++) {
                sum += x;
            }
            return sum;
        }

        public static void main(String args[]) {
            for (int i = 0; i < 20_000; i++) {
                test_loop(0, 1);
            }
            test_loop(Integer.MIN_VALUE, 0);
            test_loop(-1, Integer.MAX_VALUE);
            test_loop(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }
}
