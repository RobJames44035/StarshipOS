/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8296442
 * @summary EncryptedPrivateKeyInfo can be created with an uninitialized AlgorithmParameters
 * @modules java.base/sun.security.x509
 */

import sun.security.x509.AlgorithmId;

import java.security.AlgorithmParameters;

public class Uninitialized {
    public static void main(String[] args) throws Exception {
        AlgorithmParameters ap = AlgorithmParameters.getInstance("EC");
        boolean success;
        try {
            AlgorithmId.get(ap);
            success = true;
        } catch (Exception e) {
            success = false;
        }
        if (success) {
            throw new RuntimeException();
        }
    }
}
