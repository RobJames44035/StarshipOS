/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8196296
 * @summary Bad graph when unrolled loop bounds conflicts with range checks
 *
 * @run main/othervm -XX:-BackgroundCompilation -XX:+IgnoreUnrecognizedVMOptions -XX:LoopUnrollLimit=0 TestStripMinedBackToBackIfs
 *
 */


public class TestStripMinedBackToBackIfs {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test(100);
        }
    }

    private static double test(int limit) {
        double v = 1;
        for (int i = 0; i < limit; i++) {
            v = v * 4;
            // We don't want this test to be merged with identical
            // loop end test
            if (i+1 < limit) {
                v = v * 2;
            }
        }
        return v;
    }
}
