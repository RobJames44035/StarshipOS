/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4503229 8220016
 * @library /test/lib
 * @summary default RSA KeyFactory can return broken RSAPrivateCrtKey objects
 *      This test was taken directly from the bug report, which
 *      was fixed in the crippled JSAFE provider, and needed
 *      to be brought forward into SunRsaSign (was JSSE).
 * @author Brad Wetmore
 */

import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;
import java.math.BigInteger;
import jdk.test.lib.security.SecurityUtils;

public class BrokenRSAPrivateCrtKey {
    public static void main(String[] args) throws Exception {
        String kpgAlgorithm = "RSA";
        KeyPairGenerator generator =
                KeyPairGenerator.getInstance(kpgAlgorithm,
                        System.getProperty("test.provider.name", "SunRsaSign"));
        generator.initialize(SecurityUtils.getTestKeySize(kpgAlgorithm));

        KeyPair pair = generator.generateKeyPair();

        RSAPrivateCrtKey privatekey = (RSAPrivateCrtKey) pair.getPrivate();

        RSAPrivateCrtKeySpec spec =
                new RSAPrivateCrtKeySpec(privatekey.getModulus(),
                privatekey.getPublicExponent(),
                privatekey.getPrivateExponent(),
                privatekey.getPrimeP(), privatekey.getPrimeQ(),
                privatekey.getPrimeExponentP(),
                privatekey.getPrimeExponentQ(),
                privatekey.getCrtCoefficient());

        KeyFactory factory = KeyFactory.getInstance("RSA",
                System.getProperty("test.provider.name", "SunRsaSign"));

        PrivateKey privatekey2 = factory.generatePrivate(spec);

        BigInteger pe =
                ((RSAPrivateCrtKey) privatekey2).getPublicExponent();

        System.out.println("public exponent: " + pe);
    }
}
