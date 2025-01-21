/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8214189
 * @summary test/hotspot/jtreg/compiler/intrinsics/mathexact/MulExactLConstantTest.java fails on Windows x64 when run with -XX:-TieredCompilation
 *
 * @run main/othervm -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:-UseOnStackReplacement MultiplyByConstantLongMax
 *
 */

public class MultiplyByConstantLongMax {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            if (test(1) != Long.MAX_VALUE) {
                throw new RuntimeException("incorrect result");
            }
        }
    }

    private static long test(long v) {
        return v * Long.MAX_VALUE;
    }
}
