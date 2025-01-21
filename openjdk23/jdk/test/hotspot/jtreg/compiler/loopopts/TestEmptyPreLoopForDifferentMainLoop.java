/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8327423
 * @summary Test empty loop removal of pre-loop, with different main-loop after it.
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.loopopts.TestEmptyPreLoopForDifferentMainLoop::test
 *      compiler.loopopts.TestEmptyPreLoopForDifferentMainLoop
 * @run main compiler.loopopts.TestEmptyPreLoopForDifferentMainLoop
 */

package compiler.loopopts;

public class TestEmptyPreLoopForDifferentMainLoop {
    static int sink;

    public static void main(String args[]) {
        test(false);
    }

    static void test(boolean flag) {
        int x = 8;
        for (int j = 0; j < 100; j++) {
            for (int k = 0; k < 100; k++) {
                if (flag) {
                    x += k;
                    sink = 42;
                }
            }
            if (flag) {
                break;
            }
        }
    }
}
