/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8260407
 * @summary cmp != __null && cmp->Opcode() == Op_CmpL failure with -XX:StressLongCountedLoop=200000000 in lucene
 *
 * @run main/othervm -XX:-BackgroundCompilation TestLongCountedLoopSplitIf
 *
 */

public class TestLongCountedLoopSplitIf {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test(0, 100);
            test_helper(100, 0, 0);
        }
    }

    private static float test(long start, long stop) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
            }
        }
        float res = 1;

        long l = start;

        for (; ; ) {
            res = test_helper(l, stop, res);
            if (shouldStop(l, stop)) {
                break;
            }
            l++;
        }
        return res;
    }

    private static boolean shouldStop(long l, long stop) {
        return l >= stop;
    }

    private static float test_helper(long l, long stop, float res) {
        if (l < stop) {
            res += l;
        } else {
            res *= l;
        }
        return res;
    }
}
