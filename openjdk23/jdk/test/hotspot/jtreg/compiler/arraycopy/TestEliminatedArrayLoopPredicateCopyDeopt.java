/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8134974
 * @summary Cannot pin eliminated arraycopy loads for deopt state in uncommon trap path if it is a loop predicate unc
 *
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseOnStackReplacement
 *                   compiler.arraycopy.TestEliminatedArrayLoopPredicateCopyDeopt
 */

package compiler.arraycopy;

public class TestEliminatedArrayLoopPredicateCopyDeopt {

    static boolean test(int[] array_src) {
        int[] array_dst = new int[10];
        System.arraycopy(array_src, 0, array_dst, 0, 10);

        for (int i = 0; i < 100; i++) {
            array_src[i] = i;
        }
        if (array_dst[0] == 0) {
            return true;
        }
        return false;
    }

    static public void main(String[] args) {
        int[] array_src = new int[100];
        for (int i = 0; i < 20000; i++) {
            test(array_src);
        }
    }
}
