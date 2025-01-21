/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/**
 * @test
 * @bug     5078280
 * @library /test/lib
 * @summary make sure generated key pairs are exactly the requested length
 * @author  Andreas Sterbenz
 */

import java.security.*;
import java.security.interfaces.*;
import jdk.test.lib.security.SecurityUtils;

public class TestKeyPairGeneratorLength {
    private static final String KPG_ALGORITHM = "RSA";
    private static final int KEY_LENGTH = SecurityUtils.getTestKeySize(KPG_ALGORITHM);

    public static void main(String[] args) throws Exception {
        test(KEY_LENGTH);
        test(KEY_LENGTH + 1);
        System.out.println("Done.");
    }

    private static void test(int len) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(KPG_ALGORITHM,
                        System.getProperty("test.provider.name", "SunRsaSign"));
        kpg.initialize(len);
        for (int i = 0; i < 6; i++) {
            System.out.println("Generating keypair " + len + " bit keypair " + (i + 1) + "...");
            KeyPair kp = kpg.generateKeyPair();
            RSAPublicKey key = (RSAPublicKey)kp.getPublic();
            int k = key.getModulus().bitLength();
            if (k != len) {
                throw new Exception("length mismatch: " + k);
            }
        }
    }

}
