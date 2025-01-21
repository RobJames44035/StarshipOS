/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6329006
 * @summary verify that NSS no-db mode works correctly
 * @author Andreas Sterbenz
 * @library /test/lib ..
 * @modules jdk.crypto.cryptoki
 * @run main/othervm Crypto
 */

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.Signature;

public class Crypto extends SecmodTest {

    public static void main(String[] args) throws Exception {
        if (initSecmod() == false) {
            return;
        }

        String configName = BASE + SEP + "nsscrypto.cfg";
        Provider p = getSunPKCS11(configName);

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", p);
        KeyPair kp = kpg.generateKeyPair();

        System.out.println(kp.getPublic());
        System.out.println(kp.getPrivate());

        byte[] data = generateData(2048);

        Signature sig = Signature.getInstance("SHA1withRSA", p);
        sig.initSign(kp.getPrivate());

        sig.update(data);
        byte[] s = sig.sign();
        System.out.println("signature: " + toString(s));

        sig.initVerify(kp.getPublic());
        sig.update(data);
        boolean ok = sig.verify(s);
        if (ok == false) {
            throw new Exception("Signature verification failed");
        }

        System.out.println("OK");
    }

}
