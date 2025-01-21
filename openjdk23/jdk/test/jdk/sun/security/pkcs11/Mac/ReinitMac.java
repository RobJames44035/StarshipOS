/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4856966 8242332
 * @summary
 * @author Andreas Sterbenz
 * @library /test/lib ..
 * @key randomness
 * @modules jdk.crypto.cryptoki
 * @run main/othervm ReinitMac
 */

import java.security.Provider;
import java.util.Random;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

public class ReinitMac extends PKCS11Test {

    public static void main(String[] args) throws Exception {
        main(new ReinitMac(), args);
    }

    @Override
    public void main(Provider p) throws Exception {
        List<String> algorithms = getSupportedAlgorithms("Mac", "Hmac", p);
        Random random = new Random();
        byte[] data = new byte[10 * 1024];
        random.nextBytes(data);

        boolean success = true;
        for (String alg : algorithms) {
            SecretKey skey = generateKey(alg, 16);
            try {
                doTest(alg, p, skey, data);
            } catch (Exception e) {
                System.out.println("Unexpected exception: " + e);
                e.printStackTrace();
                success = false;
            }
        }

        if (!success) {
            throw new RuntimeException("Test failed");
        } else {
            System.out.println("All tests passed");
        }
    }

    private void doTest(String alg, Provider p, SecretKey key, byte[] data)
            throws Exception {
        System.out.println("Testing " + alg);
        Mac mac = Mac.getInstance(alg, p);
        mac.init(key);
        mac.init(key);
        mac.update(data);
        mac.init(key);
        mac.doFinal();
        mac.doFinal();
        mac.update(data);
        mac.doFinal();
        mac.reset();
        mac.reset();
        mac.init(key);
        mac.reset();
        mac.update(data);
        mac.reset();
    }
}
