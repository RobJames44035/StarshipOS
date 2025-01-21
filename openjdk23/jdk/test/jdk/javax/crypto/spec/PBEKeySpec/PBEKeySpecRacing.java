/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 6383195
 * @summary javax.crypto.spec.PBEKeySpec is not thread safe
 */

/*
 * Use one thread to clear the password, another to check the password state.
 *
 * If a partially cleared password is ever obtained, fail the test.
 */
import java.util.Arrays;
import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecRacing {
    private static final int NUMTESTS = 1000;
    private static final int PASSWORD_LEN = 25;

    private static PBEKeySpec keySpec;

    private static final char[] password;

    static {
        password = new char[PASSWORD_LEN];
        Arrays.fill(password, 'A');
    }

    // flag for failed test case.

    private static volatile char[] failed = null;

    public static void main(String[] args) {
        System.out.println("Testing: ");
        for (int i = 0; i < NUMTESTS; i++) {
            keySpec = new PBEKeySpec(password);
            Thread reader = new Thread(() -> {
                try {
                    // Repeat until state changes or failure seen
                    while (true) {
                        char[] pbePass = keySpec.getPassword();
                        if (!Arrays.equals(password, pbePass)) {
                            failed = pbePass;
                            return;
                        }
                    }
                } catch (IllegalStateException e) {
                    System.out.print(".");
                }
            });
            Thread clearer = new Thread(() -> keySpec.clearPassword());
            reader.start();
            clearer.start();
            try {
                reader.join();
                clearer.join();
            } catch (InterruptedException e) {
                // Swallow
            }

            if (failed != null) {
                throw new RuntimeException(
                        "Inconsistent Password: " + Arrays.toString(failed));
            }

            // avoid long output lines.
            if ((i % 80) == 79) {
                System.out.println();
            }
        }
        System.out.println("Test PASSED");
    }
}
