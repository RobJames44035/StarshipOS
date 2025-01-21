/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4856966 8242332
 * @summary test that reinitializing Signatures works correctly
 * @author Andreas Sterbenz
 * @library /test/lib ..
 * @key randomness
 * @modules jdk.crypto.cryptoki
 * @run main ReinitSignature
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Random;

public class ReinitSignature extends PKCS11Test {

    public static void main(String[] args) throws Exception {
        main(new ReinitSignature());
    }

    public void main(Provider p) throws Exception {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", p);
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        PrivateKey privateKey = kp.getPrivate();
        PublicKey publicKey = kp.getPublic();
        Signature sig = Signature.getInstance("SHA256withRSA", p);
        byte[] data = new byte[10 * 1024];
        new Random().nextBytes(data);
        sig.initSign(privateKey);
        sig.initSign(privateKey);
        sig.update(data);
        sig.initSign(privateKey);
        sig.update(data);
        byte[] signature = sig.sign();
        sig.update(data);
        sig.initSign(privateKey);
        sig.update(data);
        sig.sign();
        sig.sign();
        sig.initSign(privateKey);
        sig.sign();

        System.out.println("All tests passed");
    }

}
