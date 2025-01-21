/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4160382
 * @summary keytool is hanging under win32
 *      SeedGenerator causes the system to hang on win32 because
 *      it accesses AWT
 *
 * if the test returns, then it passed.
 * if the test never returns (hangs forever), then it failed.
 */

public class Awt_Hang_Test {
    public static void main(String args[]) {
        java.security.SecureRandom sr = new java.security.SecureRandom();
        sr.nextBytes(new byte[5]);
    }
}
