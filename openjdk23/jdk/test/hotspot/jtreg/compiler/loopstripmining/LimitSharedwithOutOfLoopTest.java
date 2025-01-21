/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8193597
 * @summary limit test is shared with out of loop if
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-BackgroundCompilation -XX:LoopUnrollLimit=0 -XX:+UseCountedLoopSafepoints -XX:LoopStripMiningIter=1000 LimitSharedwithOutOfLoopTest
 *
 */

public class LimitSharedwithOutOfLoopTest {
    public static void main(String[] args) {
        boolean[] array1 = new boolean[2001];
        boolean[] array2 = new boolean[2001];
        boolean[] array3 = new boolean[2001];
        array2[1000] = true;
        array3[2000] = true;
        for (int i = 0; i < 20_000; i++) {
            if (test(2000, array1)) {
                throw new RuntimeException("bad return");
            }
            if (!test(2000, array2)) {
                throw new RuntimeException("bad return");
            }
            if (test(2000, array3)) {
                throw new RuntimeException("bad return");
            }
        }
    }

    static volatile boolean barrier;

    private static boolean test(int limit, boolean[] array) {
        for (int i = 0; i < limit;) {
            i++;
            if (array[i]) {
                // Same test as end of loop test. When loop is strip
                // mined, this must not become the end of inner loop
                // test.
                if (i < limit) {
                    return true;
                }
                return false;
            }
            barrier = true;
        }
        return false;
    }
}
