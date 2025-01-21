/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8291665
 * @summary C2: assert compiling SSLEngineInputRecord::decodeInputRecord
 * @run main/othervm -Xbatch TestNewArrayOutsideLoopValidLengthTestInLoop
 */

import java.util.Arrays;

public class TestNewArrayOutsideLoopValidLengthTestInLoop {
    private static volatile int barrier;

    public static void main(String[] args) {
        boolean[] allFalse = new boolean[100];
        boolean[] allTrue = new boolean[100];
        Arrays.fill(allTrue, true);
        for (int i = 0; i < 20_000; i++) {
            test1(allFalse, allFalse, true);
            test1(allTrue, allFalse, true);
            test1(allFalse, allTrue, true);
            test1(allFalse, allFalse, false);
        }
    }

    private static int[] test1(boolean[] flags1, boolean[] flags2, boolean flag) {
        for (int i = 1; i < 100; i *= 2) {
            boolean f = false;
            int j = i;
            if (flags1[i]) {
                barrier = 1;
                f = true;
                j = i / 2;
            }
            if (flag) {
                barrier = 1;
            }
            if (f) {
                return new int[j];
            }
            if (flags2[i]) {
                return new int[j];
            }
        }
        return null;
    }
}
