/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

// This is not the real sun.security.provider.SeedGenerator class. Used by
// ../../../../CommonSeeder.java only.
package sun.security.provider;

public class SeedGenerator {

    static int count = 100;
    static int lastCount = 100;

    public static void generateSeed(byte[] result) {
        count--;
    }

    /**
     * Confirms genEntropy() has been called {@code less} times
     * since last check.
     */
    public static void checkUsage(int less) throws Exception {
        if (lastCount != count + less) {
            throw new Exception(String.format(
                    "lastCount = %d, count = %d, less = %d",
                    lastCount, count, less));
        }
        lastCount = count;
    }

    // Needed by AbstractDrbg.java
    static byte[] getSystemEntropy() {
        return new byte[20];
    }
}
