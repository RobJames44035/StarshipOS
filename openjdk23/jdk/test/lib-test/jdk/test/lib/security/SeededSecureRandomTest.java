/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
import jdk.test.lib.Asserts;
import jdk.test.lib.security.SeededSecureRandom;

/*
 * @test
 * @library /test/lib
 * @run main/othervm SeededSecureRandomTest
 */
public class SeededSecureRandomTest {

    private static final String PROP = "secure.random.seed";

    public static void main(String[] args) throws Exception {
        try {
            System.clearProperty(PROP);
            Asserts.assertNE(get(), get()); // random seed (different)
            Asserts.assertEQ(get(1L), get(1L)); // same seed
            Asserts.assertEQ(get(10L), get(10L)); // same seed
            Asserts.assertNE(get(1L), get(10L)); // different seed

            System.setProperty(PROP, "10");
            Asserts.assertEQ(get(), get()); // seed set by system property
            Asserts.assertNE(get(), get(1L)); // seed set not system property
            Asserts.assertEQ(get(), get(10L)); // seed set same as system property
            Asserts.assertEQ(get(1L), get(1L)); // same seed
            Asserts.assertEQ(get(10L), get(10L)); // same seed
            Asserts.assertNE(get(1L), get(10L)); // different seed
        } finally {
            System.clearProperty(PROP);
        }
    }

    static int get() {
        return new SeededSecureRandom(SeededSecureRandom.seed()).nextInt();
    }

    static int get(long seed) {
        return new SeededSecureRandom(seed).nextInt();
    }
}
