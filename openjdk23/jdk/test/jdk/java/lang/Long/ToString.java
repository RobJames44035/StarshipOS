/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8136500 8310929
 * @summary Test Long.toString method for both compact and non-compact strings
 * @run junit/othervm -XX:+CompactStrings ToString
 * @run junit/othervm -XX:-CompactStrings ToString
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToString {

    @Test
    public void testBase10() {
        test("-9223372036854775808", Long.MIN_VALUE);
        test("9223372036854775807",  Long.MAX_VALUE);
        test("0", 0);

        // Wiggle around the exponentially increasing base.
        final int LIMIT = (1 << 15);
        long base = 10000;
        while (base < Long.MAX_VALUE / 10) {
            for (int d = -LIMIT; d < LIMIT; d++) {
                long c = base + d;
                if (c > 0) {
                    buildAndTest(c);
                }
            }
            base *= 10;
        }

        for (int c = 1; c < LIMIT; c++) {
            buildAndTest(Long.MAX_VALUE - LIMIT + c);
        }
    }

    private static void buildAndTest(long c) {
        if (c <= 0) {
            throw new IllegalArgumentException("Test bug: can only handle positives, " + c);
        }

        StringBuilder sbN = new StringBuilder();
        StringBuilder sbP = new StringBuilder();

        long t = c;
        while (t > 0) {
            char digit = (char) ('0' + (t % 10));
            sbN.append(digit);
            sbP.append(digit);
            t = t / 10;
        }

        sbN.append("-");
        sbN.reverse();
        sbP.reverse();

        test(sbN.toString(), -c);
        test(sbP.toString(), c);
    }

    private static void test(String expected, long value) {
        assertEquals(expected, Long.toString(value));
    }
}
