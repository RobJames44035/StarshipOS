/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6695485 8242332
 * @summary Make sure initSign/initVerify() check RSA key lengths
 * @author Yu-Ching Valerie Peng
 * @library /test/lib ..
 * @modules jdk.crypto.cryptoki
 * @run main/othervm TestRSAKeyLength
 */

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignedObject;

public class TestRSAKeyLength extends PKCS11Test {

    public static void main(String[] args) throws Exception {
        main(new TestRSAKeyLength(), args);
    }

    @Override
    public void main(Provider p) throws Exception {

        boolean isValidKeyLength[] = {
                true, true, true, false, false, true, true, false, false
        };
        String algos[] = {
                "SHA1withRSA", "SHA224withRSA", "SHA256withRSA",
                "SHA384withRSA", "SHA512withRSA", "SHA3-224withRSA",
                "SHA3-256withRSA", "SHA3-384withRSA", "SHA3-512withRSA"
        };
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", p);
        kpg.initialize(512);
        KeyPair kp = kpg.generateKeyPair();
        PrivateKey privKey = kp.getPrivate();
        PublicKey pubKey = kp.getPublic();

        if (algos.length != isValidKeyLength.length) {
            throw new Exception("Internal Error: number of test algos" +
                " and results length mismatch!");
        }
        for (int i = 0; i < algos.length; i++) {
            Signature sig = Signature.getInstance(algos[i], p);
            System.out.println("Testing RSA signature " + algos[i]);
            try {
                sig.initSign(privKey);
                if (!isValidKeyLength[i]) {
                    throw new Exception("initSign: Expected IKE not thrown!");
                }
            } catch (InvalidKeyException ike) {
                if (isValidKeyLength[i]) {
                    throw new Exception("initSign: Unexpected " + ike);
                }
            }
            try {
                sig.initVerify(pubKey);
                if (!isValidKeyLength[i]) {
                    throw new RuntimeException("initVerify: Expected IKE not thrown!");
                }
                new SignedObject("Test string for getSignature test.", privKey, sig);
            } catch (InvalidKeyException ike) {
                if (isValidKeyLength[i]) {
                    throw new Exception("initSign: Unexpected " + ike);
                }
            }
        }
    }
}
