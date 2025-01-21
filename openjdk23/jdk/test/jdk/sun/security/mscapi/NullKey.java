/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.Signature;
import java.util.List;

/**
 * @test
 * @bug 8225180
 * @requires os.family == "windows"
 * @summary SunMSCAPI Signature should throw InvalidKeyException when
 *          initialized with a null key
 */

public class NullKey {
    public static void main(String[] args) throws Exception {
        for (String alg : List.of(
                "SHA256withRSA", "SHA256withECDSA", "RSASSA-PSS")) {
            Signature sig = Signature.getInstance(alg, "SunMSCAPI");
            try {
                sig.initSign(null);
            } catch (InvalidKeyException e) {
                // Expected
            }
            try {
                sig.initVerify((PublicKey)null);
            } catch (InvalidKeyException e) {
                // Expected
            }
        }
    }
}
