/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
import jdk.test.lib.Asserts;
import jdk.test.lib.Utils;
import jdk.test.lib.security.FixedSecureRandom;

/*
 * @test
 * @library /test/lib
 * @summary ensure FixedSecureRandom works as expected
 */
public class FixedSecureRandomTest {
    public static void main(String[] args) throws Exception {
        var fsr = new FixedSecureRandom(new byte[] {1, 2, 3},
                new byte[] {4, 5, 6});
        var b1 = new byte[2];
        fsr.nextBytes(b1);
        Asserts.assertEqualsByteArray(new byte[] {1, 2}, b1);
        Asserts.assertTrue(fsr.hasRemaining());
        fsr.nextBytes(b1);
        Asserts.assertEqualsByteArray(new byte[] {3, 4}, b1);
        Asserts.assertTrue(fsr.hasRemaining());
        fsr.nextBytes(b1);
        Asserts.assertEqualsByteArray(new byte[] {5, 6}, b1);
        Asserts.assertFalse(fsr.hasRemaining());
        Utils.runAndCheckException(() -> fsr.nextBytes(b1),
                IllegalStateException.class);
    }
}
