/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8331575
 * @summary C2: crash when ConvL2I is split thru phi at LongCountedLoop
 * @run main/othervm -XX:-BackgroundCompilation -XX:-TieredCompilation -XX:-UseOnStackReplacement
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressGCM -XX:StressSeed=92643864 TestLongCountedLoopConvL2I
 * @run main/othervm -XX:-BackgroundCompilation -XX:-TieredCompilation -XX:-UseOnStackReplacement
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressGCM TestLongCountedLoopConvL2I
 * @run main/othervm -XX:-BackgroundCompilation -XX:-TieredCompilation
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressGCM TestLongCountedLoopConvL2I
 */

public class TestLongCountedLoopConvL2I {
    private static volatile int volatileField;

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            testHelper1(42);
            test1(0);
        }
    }

    private static int test1(int res) {
        int k = 1;
        for (; k < 2; k *= 2) {
        }
        long i = testHelper1(k);
        for (; i > 0; i--) {
            res += 42 / ((int) i);
            for (int j = 1; j < 10; j *= 2) {

            }
        }
        return res;
    }

    private static long testHelper1(int k) {
        if (k == 2) {
            return 100;
        } else {
            return 99;
        }
    }
}
