/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8268360
 * @summary Test node placement when its use is inside infinite loop.
 * @library /test/lib
 * @run main/othervm -XX:CompileCommand=compileonly,compiler.loopopts.TestInfLoopNearUsePlacement::test
 *                   compiler.loopopts.TestInfLoopNearUsePlacement
 */

package compiler.loopopts;

import jdk.test.lib.Utils;

public class TestInfLoopNearUsePlacement {

    static void test() {
        long loa[] = new long[42];

        try {
            for (int i = 0; i < 42; i++) {
                Thread.sleep(1);
                loa[i] = 42L;
            }
        } catch (InterruptedException e) {}

        loa[0] = 1L;
        // Infinite loop: loop's variable is reset on each iteration
        for (int i = 0; i < 21; i++) {
            loa[0] += 1L;
            i = 1;
        }
    }

    public static void main(String[] args) throws Exception {
        // Execute test in own thread because it contains an infinite loop
        Thread thread = new Thread() {
            public void run() {
                for (int i = 0; i < 100; ++i) {
                    test();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        // Give thread some time to trigger compilation
        Thread.sleep(Utils.adjustTimeout(500));
    }
}
