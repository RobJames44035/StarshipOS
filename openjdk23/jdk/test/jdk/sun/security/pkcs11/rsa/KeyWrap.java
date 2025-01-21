/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6231216
 * @summary Verify key wrapping (of extractable keys) works for RSA/PKCS1
 * @author Andreas Sterbenz
 * @library /test/lib ..
 * @key randomness
 * @modules jdk.crypto.cryptoki
 * @run main/othervm KeyWrap
 */

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyWrap extends PKCS11Test {

    @Override
    public void main(Provider p) throws Exception {
        try {
            Cipher.getInstance("RSA/ECB/PKCS1Padding", p);
        } catch (GeneralSecurityException e) {
            System.out.println("Not supported by provider, skipping");
            return;
        }
        KeyPair kp;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", p);
            kpg.initialize(512);
            kp = kpg.generateKeyPair();
        } catch (Exception e) {
            try {
                System.out.println("Could not generate KeyPair on provider " + p + ", trying migration");
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(512);
                kp = kpg.generateKeyPair();
                KeyFactory kf = KeyFactory.getInstance("RSA", p);
                PublicKey pub = (PublicKey)kf.translateKey(kp.getPublic());
                PrivateKey priv = (PrivateKey)kf.translateKey(kp.getPrivate());
                kp = new KeyPair(pub, priv);
            } catch (NoSuchAlgorithmException | InvalidKeyException ee) {
                ee.printStackTrace();
                System.out.println("Provider does not support RSA, skipping");
                return;
            }
        }
        System.out.println(kp);
        Random r = new Random();
        byte[] b = new byte[16];
        r.nextBytes(b);
        String alg = "AES";
        SecretKey key = new SecretKeySpec(b, alg);

        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding", p);
//      Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.WRAP_MODE, kp.getPublic());
        byte[] wrapped = c.wrap(key);
        System.out.println("wrapped: " + wrapped.length);

        c.init(Cipher.UNWRAP_MODE, kp.getPrivate());
        Key unwrapped = c.unwrap(wrapped, alg, Cipher.SECRET_KEY);
        System.out.println("unwrapped: " + unwrapped);

        boolean eq = key.equals(unwrapped);
        System.out.println(eq);
        if (eq == false) {
            throw new Exception("Unwrapped key does not match original key");
        }
    }

    public static void main(String[] args) throws Exception {
        main(new KeyWrap(), args);
    }

}
