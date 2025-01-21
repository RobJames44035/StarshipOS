/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8308010
 * @summary X509Key and PKCS8Key allows garbage bytes at the end
 * @library /test/lib
 */

import jdk.test.lib.Utils;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class LongPKCS8orX509KeySpec {

    public static void main(String[] argv) throws Exception {
        var g = KeyPairGenerator.getInstance("EC");
        var f = KeyFactory.getInstance("EC");
        Utils.runAndCheckException(() -> f.generatePublic(new X509EncodedKeySpec(
                Arrays.copyOf(g.generateKeyPair().getPublic().getEncoded(), 1000))),
                InvalidKeySpecException.class);
        Utils.runAndCheckException(() -> f.generatePrivate(new PKCS8EncodedKeySpec(
                Arrays.copyOf(g.generateKeyPair().getPrivate().getEncoded(), 1000))),
                InvalidKeySpecException.class);
    }
}
