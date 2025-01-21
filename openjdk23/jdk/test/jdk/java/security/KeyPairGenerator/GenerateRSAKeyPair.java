/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4297026
 * @library /test/lib
 * @summary Make sure that RSA Keypair generation using
 * java.security.spec.RSAKeyGenParameterSpec passes
 */

import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.spec.RSAKeyGenParameterSpec;
import jdk.test.lib.security.SecurityUtils;

public class GenerateRSAKeyPair {

    public static void main(String[] args) throws Exception {

        String kpgAlgorithm = "RSA";
        RSAKeyGenParameterSpec rsaSpec =
        new RSAKeyGenParameterSpec (SecurityUtils.getTestKeySize(kpgAlgorithm),
                RSAKeyGenParameterSpec.F4);
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(kpgAlgorithm,
                System.getProperty("test.provider.name", "SunRsaSign"));
        kpg.initialize(rsaSpec);

        // test generateKeyPair
        KeyPair kpair = kpg.generateKeyPair();
        if (kpair == null) {
            throw new Exception("no keypair generated");
        }
    }
}
