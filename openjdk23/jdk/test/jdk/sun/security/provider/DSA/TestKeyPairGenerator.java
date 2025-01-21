/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4800108 8072452 8181048
 * @summary verify that precomputed DSA parameters are always used (512, 768,
 *          1024, 2048, 3072 bit)
 * @run main/othervm/timeout=15 TestKeyPairGenerator
 */

//
// This fix is really a performance fix, so this test is not foolproof.
// Without the precomputed parameters, it will take a minute or more
// (unless you have a very fast machine).  With the fix, the test should
// complete in less than 2 seconds.  Use 15 second timeout to leave some room.
//

import java.security.*;
import java.security.interfaces.*;

public class TestKeyPairGenerator {

    private static void checkKeyLength(KeyPair kp, int len) throws Exception {
        DSAPublicKey key = (DSAPublicKey)kp.getPublic();
        int n = key.getParams().getP().bitLength();
        System.out.println("Key length: " + n);
        if (len != n) {
            throw new Exception("Wrong key length");
        }
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        KeyPairGenerator kpg;
        KeyPair kp;
        // problem was when not calling initialize()
        // do that twice to artifically inflate the time
        // on JDKs that do not have the fix
        kpg = KeyPairGenerator.getInstance("DSA",
                System.getProperty("test.provider.name", "SUN"));
        kp = kpg.generateKeyPair();

        kpg = KeyPairGenerator.getInstance("DSA",
                System.getProperty("test.provider.name", "SUN"));
        kp = kpg.generateKeyPair();

        // some other basic tests
        kp = kpg.generateKeyPair();

        kpg.initialize(1024);
        kp = kpg.generateKeyPair();
        checkKeyLength(kp, 1024);

        kpg.initialize(768);
        kp = kpg.generateKeyPair();
        checkKeyLength(kp, 768);

        kpg.initialize(512);
        kp = kpg.generateKeyPair();
        checkKeyLength(kp, 512);

        kpg.initialize(2048);
        kp = kpg.generateKeyPair();
        checkKeyLength(kp, 2048);

        kpg.initialize(3072);
        kp = kpg.generateKeyPair();
        checkKeyLength(kp, 3072);

        long stop = System.currentTimeMillis();
        System.out.println("Time: " + (stop - start) + " ms.");
    }
}
