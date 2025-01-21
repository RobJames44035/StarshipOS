/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @key stress randomness
 * @bug 8182036
 * @summary Load from initializing arraycopy uses wrong memory state
 *
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseOnStackReplacement -XX:+UnlockDiagnosticVMOptions -XX:+IgnoreUnrecognizedVMOptions -XX:+StressGCM -XX:+StressLCM TestInitializingACLoadWithBadMem
 *
 */

import java.util.Arrays;

public class TestInitializingACLoadWithBadMem {
    static Object test_dummy;
    static int test1() {
        int[] src = new int[10];
        test_dummy = src;
        int[] dst = new int[10];
        src[1] = 0x42;
        // arraycopy generates a load from src/store to dst which must
        // be after the store to src above.
        System.arraycopy(src, 1, dst, 1, 9);
        return dst[1];
    }

    static public void main(String[] args) {
        int[] src = new int[10];
        for (int i = 0; i < 20000; i++) {
            int res = test1();
            if (res != 0x42) {
                throw new RuntimeException("bad result: " + res + " != " + 0x42);
            }
        }
    }
}
