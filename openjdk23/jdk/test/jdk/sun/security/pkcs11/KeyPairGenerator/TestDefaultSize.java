/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8267319
 * @summary Ensure that DSA/RSA/DH/EC KPG in PKCS11 provider uses the
 *     same default key length
 * @library /test/lib ..
 * @modules java.base/sun.security.util
 *          jdk.crypto.cryptoki
 * @run main TestDefaultSize
 */

import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.PrivateKey;
import java.security.interfaces.*;
import javax.crypto.interfaces.DHKey;

import static sun.security.util.SecurityProviderConstants.*;

public class TestDefaultSize extends PKCS11Test {

    @Override
    public void main(Provider p) throws Exception {
        System.out.println("Testing " + p.getName());

        String[] ALGOS = { "DSA", "RSA", "DH", "EC" };

        for (String algo : ALGOS) {
            if (p.getService("KeyPairGenerator", algo) == null) {
                System.out.println("Skip, no support for KPG: " + algo);
                return;
            }

            KeyPairGenerator kpg = KeyPairGenerator.getInstance(algo, p);
            KeyPair kp = kpg.generateKeyPair();
            PrivateKey priv = kp.getPrivate();
            int actualSize = -1;
            int expectedSize;
            if (algo == "DSA") {
                expectedSize = DEF_DSA_KEY_SIZE;
                if (priv instanceof DSAKey) {
                    actualSize = ((DSAKey) priv).getParams().getP().bitLength();
                }
            } else if (algo == "RSA") {
                expectedSize = DEF_RSA_KEY_SIZE;
                if (priv instanceof RSAKey) {
                    actualSize = ((RSAKey) priv).getModulus().bitLength();
                }
            } else if (algo == "DH") {
                expectedSize = DEF_DH_KEY_SIZE;
                if (priv instanceof DHKey) {
                    actualSize = ((DHKey) priv).getParams().getP().bitLength();
                }
            } else if (algo == "EC") {
                expectedSize = DEF_EC_KEY_SIZE;
                if (priv instanceof ECKey) {
                    actualSize = ((ECKey) priv).getParams().getCurve()
                            .getField().getFieldSize();
                }
            } else {
                throw new RuntimeException("Error: Unrecognized algo " +
                    algo + " or opaque private key object " + priv);
            }
            if (actualSize != expectedSize) {
                throw new RuntimeException("key size check failed, got " +
                    actualSize);
            } else {
                System.out.println(algo + ": passed, " + actualSize);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        main(new TestDefaultSize(), args);
    }
}
