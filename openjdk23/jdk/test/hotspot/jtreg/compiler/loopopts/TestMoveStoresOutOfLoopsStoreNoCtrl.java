/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8134288
 * @summary Store nodes may not have a control if used to update profiling
 *
 * @run main/othervm -XX:-ProfileInterpreter -XX:-TieredCompilation
 *                   -XX:-BackgroundCompilation
 *                   compiler.loopopts.TestMoveStoresOutOfLoopsStoreNoCtrl
 */

package compiler.loopopts;

public class TestMoveStoresOutOfLoopsStoreNoCtrl {

    static void test(boolean flag) {
        for (int i = 0; i < 20000; i++) {
            if (flag) {
                int j = 0;
                do {
                    j++;
                } while(j < 10);
            }
        }
    }

    static public void main(String[] args) {
        test(false);
    }

}
