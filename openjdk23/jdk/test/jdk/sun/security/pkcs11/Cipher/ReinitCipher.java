/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4856966
 * @summary
 * @author Andreas Sterbenz
 * @library /test/lib ..
 * @key randomness
 * @modules jdk.crypto.cryptoki
 * @run main/othervm ReinitCipher
 */

import java.security.Provider;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ReinitCipher extends PKCS11Test {

    public static void main(String[] args) throws Exception {
        main(new ReinitCipher(), args);
    }

    @Override
    public void main(Provider p) throws Exception {
        if (p.getService("Cipher", "ARCFOUR") == null) {
            System.out.println("Not supported by provider, skipping");
            return;
        }
        Random random = new Random();
        byte[] data1 = new byte[10 * 1024];
        random.nextBytes(data1);
        byte[] keyData = new byte[16];
        random.nextBytes(keyData);
        SecretKeySpec key = new SecretKeySpec(keyData, "ARCFOUR");

        Cipher cipher = Cipher.getInstance("ARCFOUR", p);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.update(data1);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.update(data1);
        cipher.doFinal();
        cipher.doFinal();
        cipher.update(data1);
        cipher.doFinal();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.doFinal();

        System.out.println("All tests passed");
    }
}
