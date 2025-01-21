/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.PrintStream;
import java.lang.String;
import java.lang.System;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.KeyGenerator;
import static java.lang.System.out;

/*
 * @test
 * @bug 8048607
 * @compile ../../../com/sun/crypto/provider/Cipher/DES/TestUtility.java
 * @run main TestKGParity
 * @summary Test key generation of DES and DESEDE
 * @key randomness
 */
public class TestKGParity {

    private static final String[] ALGORITHM_ARR = {
        "deS", "DesEDE"
    };

    public static void main(String argv[]) throws Exception {

        TestKGParity test = new TestKGParity();
        test.run();
    }

    private void run() throws Exception {
        Provider[] providers = Security.getProviders();
        for (Provider p : providers) {
            String prvName = p.getName();
            if ((System.getProperty("test.provider.name") != null &&
                    prvName.equals(System.getProperty("test.provider.name"))) ||
                    (System.getProperty("test.provider.name") == null &&
                            (prvName.startsWith("SunJCE") || prvName.startsWith("SunPKCS11-")))) {
                for (String algorithm : ALGORITHM_ARR) {
                    if (!runTest(p, algorithm)) {
                        throw new RuntimeException(
                                "Test failed with provider/algorithm:"
                                        + p.getName() + "/" + algorithm);
                    } else {
                        out.println("Test passed with provider/algorithm:"
                                + p.getName() + "/" + algorithm);
                    }
                }
            }
        }
    }

    public boolean runTest(Provider p, String algo) throws Exception {
        byte[] keyValue = null;
        try {
            // Initialization
            SecureRandom sRdm = new SecureRandom();
            KeyGenerator kg = KeyGenerator.getInstance(algo, p);
            kg.init(sRdm);

            // Generate a SecretKey and retrieve its value
            keyValue = kg.generateKey().getEncoded();

            // Verify its parity in the unit of byte
            for (int i = 0; i < keyValue.length; i++) {
                if (!checkParity(keyValue[i])) {
                    out.println("Testing: "
                        + p.getName()
                        + "/"
                        + algo
                        + " failed when verify its parity in the unit of byte:"
                        + TestUtility.hexDump(keyValue, i));
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            out.println("Testing: " + p.getName() + "/" + algo
                    + " failed with unexpected exception");
            ex.printStackTrace();
            throw ex;
        }
    }

    private boolean checkParity(byte keyByte) {
        boolean even = false;
        byte[] PARITY_BIT_MASK = {
                (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08,
                (byte) 0x04, (byte) 0x02, (byte) 0x01
        };

        for (int i = 0; i < 7; i++) {
            if ((keyByte & PARITY_BIT_MASK[i]) > 0) {
                even = !even;
            }
        }
        if (keyByte < 0) {
            even = !even;
        }

        return even;
    }
}
