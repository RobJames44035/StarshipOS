/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package jdk.internal.net.http;

import java.util.Random;

/** Abstract supertype for tests that need random numbers within a given range. */
public class AbstractRandomTest {

    private static Long getSystemSeed() {
        Long seed = null;
        try {
            // note that Long.valueOf(null) also throws a NumberFormatException
            // so if the property is undefined this will still work correctly
            seed = Long.valueOf(System.getProperty("seed"));
        } catch (NumberFormatException e) {
            // do nothing: seed is still null
        }
        return seed;
    }

    private static long getSeed() {
        Long seed = getSystemSeed();
        if (seed == null) {
            seed = (new Random()).nextLong();
        }
        System.out.println("Seed from AbstractRandomTest.getSeed = "+seed+"L");
        return seed;
    }

    private static Random random = new Random(getSeed());

    protected static int randomRange(int lower, int upper) {
        if (lower > upper)
            throw new IllegalArgumentException("lower > upper");
        int diff = upper - lower;
        int r = lower + random.nextInt(diff);
        return r - (r % 8); // round down to multiple of 8 (align for longs)
    }
}
