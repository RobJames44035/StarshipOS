/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 7020614
 * @summary "-server" mode optimizer makes code hang
 *
 * @run main/othervm/timeout=30 -Xbatch compiler.c2.Test7020614
 */

package compiler.c2;

public class Test7020614 {

    private static final int ITERATIONS = 1000;
    private static int doNotOptimizeOut = 0;

    public static long bitCountShort() {
        long t0 = System.currentTimeMillis();
        int sum = 0;
        for (int it = 0; it < ITERATIONS; ++it) {
            short value = 0;
            do {
                sum += Integer.bitCount(value);
            } while (++value != 0);
        }
        doNotOptimizeOut += sum;
        return System.currentTimeMillis() - t0;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4; ++i) {
            System.out.println((i + 1) + ": " + bitCountShort());
        }
        System.out.println("doNotOptimizeOut value: " + doNotOptimizeOut);
    }
}

