/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8279062
 * @summary C2: assert(t->meet(t0) == t) failed: Not monotonic after JDK-8278413
 *
 * @run main/othervm -XX:-BackgroundCompilation TestCCPAllocateArray
 *
 */

public class TestCCPAllocateArray {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            try {
                test();
            } catch (OutOfMemoryError e) {
            }
            length(42);
        }
    }

    private static int[] test() {
        int i = 2;
        for (; i < 4; i *= 2);
        return new int[length(i)];
    }

    private static int length(int i) {
        return i == 4 ? Integer.MAX_VALUE : 0;
    }
}
