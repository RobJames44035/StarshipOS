/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8274714
 * @summary Make sure error message for protected putfield error is correct.
 * @compile putfieldProtected.jasm
 * @run main/othervm -Xverify:remote PutfieldProtectedTest
 */

// Test that an int[] is not assignable to byte[].
public class PutfieldProtectedTest {

    public static void main(String args[]) throws Throwable {
        try {
            Class newClass = Class.forName("other.putfieldProtected");
            throw new RuntimeException("Expected VerifyError exception not thrown");
        } catch (java.lang.VerifyError e) {
            if (!e.getMessage().contains("Bad access to protected data in putfield")) {
                throw new RuntimeException("wrong exception: " + e.getMessage());
            }
        }
    }
}
