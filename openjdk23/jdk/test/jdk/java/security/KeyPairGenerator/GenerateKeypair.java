/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4221800
 * @library /test/lib
 * @summary Test restored generateKeyPair method
 */

import java.security.KeyPairGenerator;
import java.security.KeyPair;
import jdk.test.lib.security.SecurityUtils;

public class GenerateKeypair {

    public static void main(String[] args) throws Exception {

        String kpgAlgorithm = "DSA";
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(kpgAlgorithm);
        kpg.initialize(SecurityUtils.getTestKeySize(kpgAlgorithm));

        // test generateKeyPair
        KeyPair kpair = kpg.generateKeyPair();
        if (kpair == null) {
            throw new Exception("no keypair generated");
        }

        // test genKeyPair
        kpair = kpg.genKeyPair();
        if (kpair == null) {
            throw new Exception("no keypair generated");
        }
    }
}
