/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import static jdk.test.lib.Utils.runAndCheckException;

import java.lang.Override;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.util.List;
import java.util.Map;

/*
 * @test
 * @library /test/lib
 * @bug 7004967 8329754
 * @summary SecureRandom should be more explicit about threading
 */

public class ThreadSafe {
    public static void main(String[] args) throws Exception {
        Provider p = new P();
        NoSync.test(SecureRandom.getInstance("S1", p), 5, 5);
        NoSync.test(SecureRandom.getInstance("AliasS1", p), 5, 5);

        runAndCheckException(
                () -> NoSync.test(SecureRandom.getInstance("S2", p), 5, 5),
                RuntimeException.class);

        runAndCheckException(
                () -> NoSync.test(SecureRandom.getInstance("AliasS2", p), 5, 5),
                RuntimeException.class);

        NoSync.test(SecureRandom.getInstance("S3", p), 5, 5);
        NoSync.test(SecureRandom.getInstance("AliasS3", p), 5, 5);

        runAndCheckException(
                () -> NoSync.test(SecureRandom.getInstance("S4", p), 5, 5),
                RuntimeException.class);

        runAndCheckException(
                () -> NoSync.test(SecureRandom.getInstance("AliasS4", p), 5, 5),
                RuntimeException.class);
    }

    public static class P extends Provider {
        public P() {

            super("P", 1.0d, "Haha");

            // Good. No attribute.
            put("SecureRandom.S1", S.class.getName());

            // Good. Alias of S1, should pass because S1 is not marked as ThreadSafe
            put("Alg.alias.SecureRandom.AliasS1", "S1");

            // Bad. Boasting ThreadSafe but isn't
            put("SecureRandom.S2", S.class.getName());
            put("SecureRandom.S2 ThreadSafe", "true");

            //Bad. Alias of S2, should fail because S2 is marked as ThreadSafe
            put("alg.Alias.SecureRandom.AliasS2", "S2");

            // Good. No attribute.
            putService(new Service(this, "SecureRandom", "S3",
                    S.class.getName(), List.of("AliasS3"), null));

            // Bad. Boasting ThreadSafe but isn't
            putService(new Service(this, "SecureRandom", "S4",
                    S.class.getName(), List.of("AliasS4"), Map.of("ThreadSafe", "true")));
        }
    }

    // This implementation is not itself thread safe.
    public static class S extends SecureRandomSpi {

        @Override
        protected void engineSetSeed(byte[] seed) {
            return;
        }

        private volatile boolean inCall = false;

        @Override
        protected void engineNextBytes(byte[] bytes) {
            if (inCall) {
                throw new RuntimeException("IN CALL");
            }
            inCall = true;
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                // OK
            }
            inCall = false;
        }

        @Override
        protected byte[] engineGenerateSeed(int numBytes) {
            return new byte[numBytes];
        }
    }
}
