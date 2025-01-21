/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8242332
 * @summary Check that PKCS11 Hamc KeyGenerator picks appropriate default size
 * @library /test/lib ..
 * @modules jdk.crypto.cryptoki
 * @run main/othervm HmacDefKeySizeTest
 */

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class HmacDefKeySizeTest extends PKCS11Test {

    /**
     * Request a KeyGenerator object from PKCS11 provider for Hmac algorithm,
     * and generate the SecretKey.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        main(new HmacDefKeySizeTest(), args);
    }

    @Override
    public void main(Provider p) {
        List<String> algorithms = getSupportedAlgorithms("KeyGenerator",
                "Hmac", p);
        boolean success = true;

        for (String alg : algorithms) {
            System.out.println("Testing " + alg);
            try {
                KeyGenerator kg = KeyGenerator.getInstance(alg, p);
                SecretKey k1 = kg.generateKey();
                int keysize = k1.getEncoded().length << 3;
                System.out.println("=> default key size = " + keysize);
                kg.init(keysize);
                SecretKey k2 = kg.generateKey();
                if ((k2.getEncoded().length << 3) != keysize) {
                    success = false;
                    System.out.println("keysize check failed");
                }
            } catch (Exception e) {
                System.out.println("Unexpected exception: " + e);
                e.printStackTrace();
                success = false;
            }
        }

        if (!success) {
            throw new RuntimeException("One or more tests failed");
        }
    }
}
