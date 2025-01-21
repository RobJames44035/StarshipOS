/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8213009 8237804
 * @summary Make sure SunMSCAPI keys have correct algorithm names
 * @requires os.family == "windows"
 * @library /test/lib
 * @modules jdk.crypto.mscapi
 */

import java.security.*;

import jdk.test.lib.Asserts;
import jdk.test.lib.SecurityTools;

public class KeyAlgorithms {

    private static final String ALIAS = "8213009";
    private static final String ALG = "RSA";

    public static void main(String[] arg) throws Exception {

        cleanup();
        SecurityTools.keytool("-genkeypair",
                "-storetype", "Windows-My",
                "-keyalg", ALG,
                "-alias", ALIAS,
                "-dname", "cn=" + ALIAS,
                "-noprompt").shouldHaveExitValue(0);

        try {
            test(loadKeysFromKeyStore());
        } finally {
            cleanup();
        }

        test(generateKeys());
    }

    private static void cleanup() {
        try {
            KeyStore ks = KeyStore.getInstance("Windows-MY");
            ks.load(null, null);
            ks.deleteEntry(ALIAS);
            ks.store(null, null);
        } catch (Exception e) {
            System.out.println("No such entry.");
        }
    }

    static KeyPair loadKeysFromKeyStore() throws Exception {
        KeyStore ks = KeyStore.getInstance("Windows-MY");
        ks.load(null, null);
        return new KeyPair(ks.getCertificate(ALIAS).getPublicKey(),
                (PrivateKey) ks.getKey(ALIAS, null));
    }

    static KeyPair generateKeys() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALG, "SunMSCAPI");
        return kpg.generateKeyPair();
    }

    static void test(KeyPair kp) {
        Asserts.assertEQ(kp.getPrivate().getAlgorithm(), ALG);
        Asserts.assertEQ(kp.getPublic().getAlgorithm(), ALG);
    }
}
