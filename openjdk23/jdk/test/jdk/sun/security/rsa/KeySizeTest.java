/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * @test
 * @bug 8044199
 * @summary test if the private and public key size are the same as what is set
 * through KeyPairGenerator.
 * @run main KeySizeTest 512 10
 * @run main KeySizeTest 768 10
 * @run main KeySizeTest 1024 10
 * @run main KeySizeTest 2048 5
 * @run main KeySizeTest 4096 1
 */
public class KeySizeTest {

    /**
     * ALGORITHM name, fixed as RSA.
     */
    private static final String KEYALG = "RSA";

    /**
     * JDK default RSA Provider.
     */
    private static final String PROVIDER_NAME =
            System.getProperty("test.provider.name", "SunRsaSign");

    public static void main(String[] args) throws Exception {
        int iKeyPairSize = Integer.parseInt(args[0]);
        int maxLoopCnt = Integer.parseInt(args[1]);

        int failCount = 0;
        KeyPairGenerator keyPairGen
                = KeyPairGenerator.getInstance(KEYALG, PROVIDER_NAME);
        keyPairGen.initialize(iKeyPairSize);
        // Generate RSA keypair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // Get priavte and public keys
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        try {
            if (!sizeTest(keyPair)) {
                failCount++;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            failCount++;
        }

        for (int iCnt = 0; iCnt < maxLoopCnt; iCnt++) {

            // Get keysize (modulus) of keys
            KeyFactory keyFact = KeyFactory.getInstance(KEYALG, PROVIDER_NAME);

            // Comparing binary length.
            RSAPrivateKeySpec privateKeySpec
                    = (RSAPrivateKeySpec) keyFact.getKeySpec(privateKey,
                            RSAPrivateKeySpec.class);
            int iPrivateKeySize = privateKeySpec.getModulus().bitLength();

            RSAPublicKeySpec publicKeySpec
                    = (RSAPublicKeySpec) keyFact.getKeySpec(publicKey,
                            RSAPublicKeySpec.class);
            int iPublicKeySize = publicKeySpec.getModulus().bitLength();

            if ((iKeyPairSize != iPublicKeySize) || (iKeyPairSize != iPrivateKeySize)) {
                System.err.println("iKeyPairSize : " + iKeyPairSize);
                System.err.println("Generated a " + iPrivateKeySize
                        + " bit RSA private key");
                System.err.println("Generated a " + iPublicKeySize
                        + " bit RSA public key");
                failCount++;
            }
        }

        if (failCount > 0) {
            throw new RuntimeException("There are " + failCount + " tests failed.");
        }
    }

    /**
     * @param kpair test key pair.
     * @return true if test passed. false if test failed.
     */
    private static boolean sizeTest(KeyPair kpair) {
        RSAPrivateKey priv = (RSAPrivateKey) kpair.getPrivate();
        RSAPublicKey pub = (RSAPublicKey) kpair.getPublic();

        // test the getModulus method
        if ((priv instanceof RSAKey) && (pub instanceof RSAKey)) {
            if (!priv.getModulus().equals(pub.getModulus())) {
                System.err.println("priv.getModulus() = " + priv.getModulus());
                System.err.println("pub.getModulus() = " + pub.getModulus());
                return false;
            }
        }
        return true;
    }
}
