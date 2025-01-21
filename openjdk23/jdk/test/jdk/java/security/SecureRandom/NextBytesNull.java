/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8155191
 * @summary check NPE is thrown for various methods of SecureRandom class,
 *     e.g. SecureRandom(byte[]), nextBytes(byte[]), and setSeed(byte[]).
 * @run main NextBytesNull
 */

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;

public class NextBytesNull {

    public static void main(String[] args) throws Exception {
        String test = "SecureRandom(null)";
        try {
            new SecureRandom(null);
            throw new RuntimeException("Error: NPE not thrown for " + test);
        } catch (NullPointerException e) {
            System.out.println("OK, expected NPE thrown for " + test);
        }

        // verify with an Spi impl which does not throw NPE
        SecureRandom sr = SecureRandom.getInstance("S1", new P());
        try {
            sr.nextBytes(null);
            throw new RuntimeException("Error: NPE not thrown");
        } catch (NullPointerException npe) {
            System.out.println("OK, expected NPE thrown for " + test);
        }
        try {
            sr.setSeed(null);
            throw new RuntimeException("Error: NPE not thrown for " + test);
        } catch (NullPointerException npe) {
            System.out.println("OK, expected NPE thrown for " + test);
        }
    }

    public static final class P extends Provider {
        public P() {
            super("P", 1.0d, "Test Provider without Null Check");
            put("SecureRandom.S1", S.class.getName());
        }
    }

    public static final class S extends SecureRandomSpi {
        @Override
        protected void engineSetSeed(byte[] seed) {
        }
        @Override
        protected void engineNextBytes(byte[] bytes) {
        }
        @Override
        protected byte[] engineGenerateSeed(int numBytes) {
            return new byte[numBytes];
        }
    }
}
