/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8044199 8137231
 * @summary Check same KeyPair's private key and public key have same modulus.
 * also check public key's public exponent equals to given spec's public
 * exponent. Only key size 1024 is tested with RSAKeyGenParameterSpec.F0 (3).
 * @run main SpecTest 512
 * @run main SpecTest 768
 * @run main SpecTest 1024
 * @run main SpecTest 1024 167971
 * @run main SpecTest 2048
 * @run main/timeout=240 SpecTest 4096
 * @run main/timeout=240 SpecTest 5120
 */
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAKeyGenParameterSpec;

public class SpecTest {

    /**
     * ALGORITHM name, fixed as RSA.
     */
    private static final String KEYALG = "RSA";

    /**
     * JDK default RSA Provider.
     */
    private static final String PROVIDER =
                System.getProperty("test.provider.name", "SunRsaSign");

    /**
     *
     * @param kpair test key pair
     * @param pubExponent expected public exponent.
     * @return true if test passed. false if test failed.
     */
    private static boolean specTest(KeyPair kpair, BigInteger pubExponent) {
        boolean passed = true;
        RSAPrivateKey priv = (RSAPrivateKey) kpair.getPrivate();
        RSAPublicKey pub = (RSAPublicKey) kpair.getPublic();

        // test the getModulus method
        if ((priv instanceof RSAKey) && (pub instanceof RSAKey)) {
            if (!priv.getModulus().equals(pub.getModulus())) {
                System.out.println("priv.getModulus() = " + priv.getModulus());
                System.out.println("pub.getModulus() = " + pub.getModulus());
                passed = false;
            }

            if (!pubExponent.equals(pub.getPublicExponent())) {
                System.out.println("pubExponent = " + pubExponent);
                System.out.println("pub.getPublicExponent() = "
                        + pub.getPublicExponent());
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) throws Exception {

        int size = 0;

        if (args.length >= 1) {
            size = Integer.parseInt(args[0]);
        } else {
            throw new RuntimeException("Missing keysize to test with");
        }

        BigInteger publicExponent
                = (args.length >= 2) ? new BigInteger(args[1]) : RSAKeyGenParameterSpec.F4;

        System.out.println("Running test with key size: " + size
                + " and public exponent: " + publicExponent);

        KeyPairGenerator kpg1 = KeyPairGenerator.getInstance(KEYALG, PROVIDER);
        kpg1.initialize(new RSAKeyGenParameterSpec(size, publicExponent));
        if (!specTest(kpg1.generateKeyPair(), publicExponent)) {
            throw new RuntimeException("Test failed.");
        }
    }
}
