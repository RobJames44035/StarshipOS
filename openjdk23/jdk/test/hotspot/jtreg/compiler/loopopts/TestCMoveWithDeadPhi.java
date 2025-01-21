/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8265938
 * @summary Test conditional move optimization with a TOP PhiNode.
 * @library /test/lib
 * @run main/othervm -XX:CompileCommand=compileonly,compiler.loopopts.TestCMoveWithDeadPhi::test
 *                   compiler.loopopts.TestCMoveWithDeadPhi
 */

package compiler.loopopts;

import jdk.test.lib.Utils;

public class TestCMoveWithDeadPhi {

    static void test(boolean b) {
        if (b) {
            long l = 42;
            for (int i = 0; i < 100; i++) {
                if (i < 10) {
                    l++;
                    if (i == 5) {
                        break;
                    }
                }
            }

            // Infinite loop
            for (int j = 0; j < 100; j++) {
                j--;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Execute test in own thread because it contains an infinite loop
        Thread thread = new Thread() {
            public void run() {
                for (int i = 0; i < 50_000; ++i) {
                    test((i % 2) == 0);
                }
            }
        };
        // Give thread some time to trigger compilation
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(Utils.adjustTimeout(500));
    }
}
