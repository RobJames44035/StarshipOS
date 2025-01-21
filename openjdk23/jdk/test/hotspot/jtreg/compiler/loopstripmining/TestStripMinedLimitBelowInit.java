/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8244086
 * @summary Following 8241492, strip mined loop may run extra iterations
 * @requires vm.compiler2.enabled
 *
 * @run main/othervm -XX:-BackgroundCompilation -XX:LoopUnrollLimit=0 -XX:-TieredCompilation TestStripMinedLimitBelowInit
 *
 */

public class TestStripMinedLimitBelowInit {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test1(0, 1000);
            test2(1000, 0);
        }
        int sum = test1(1000, 0);
        if (sum != 1000) {
            throw new RuntimeException("wrong result: " + sum);
        }
        sum = test2(0, 1000);
        if (sum != 0) {
            throw new RuntimeException("wrong result: " + sum);
        }
    }

    private static int test1(int start, int stop) {
        int sum = 0;
        int i = start;
        do {
            // So ciTypeFlow doesn't clone the loop head
            synchronized (new Object()) {
            }
            sum += i;
            i++;
        } while (i < stop);
        return sum;
    }

    private static int test2(int start, int stop) {
        int sum = 0;
        int i = start;
        do {
            synchronized (new Object()) {
            }
            sum += i;
            i--;
        } while (i >= stop);
        return sum;
    }
}
