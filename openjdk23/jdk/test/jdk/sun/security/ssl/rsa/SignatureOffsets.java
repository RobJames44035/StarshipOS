/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/*
 * @test
 * @bug 8050374 8220016
 * @key randomness
 * @summary This test validates signature verification
 *          Signature.verify(byte[], int, int). The test uses RandomFactory to
 *          get random set of clear text data to sign. After the signature
 *          generation, the test tries to verify signature with the above API
 *          and passing in different signature offset (0, 33, 66, 99).
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @compile ../../../../java/security/Signature/Offsets.java
 * @run main SignatureOffsets SunJSSE    MD5andSHA1withRSA
 */
public class SignatureOffsets {

    public static void main(String[] args) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException {
        Offsets.main(args);
    }
}
