/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8218723
 * @summary Use SunJCE Mac in SecretKeyFactory PBKDF2 implementation
 * @library evilprov.jar
 * @library /test/lib
 * @run main/othervm SecKeyFacSunJCEPrf
 */

import java.util.Arrays;
import java.util.HexFormat;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import java.security.Provider;
import java.security.Security;
import com.evilprovider.*;

public class SecKeyFacSunJCEPrf {

    // One of the PBKDF2 HMAC-SHA1 test vectors from RFC 6070
    private static final byte[] SALT = "16-byte salt val".getBytes();
    private static final char[] PASS = "password".toCharArray();
    private static final int ITER = 4096;
    private static final byte[] EXP_OUT =
            HexFormat.of().parseHex("D2CACD3F1D44AF293C704F0B1005338D903C688C");

    public static void main(String[] args) throws Exception {
        // Instantiate the Evil Provider and insert it in the
        // most-preferred position.
        Provider evilProv = new EvilProvider();
        System.out.println("3rd Party Provider: " + evilProv);
        Security.insertProviderAt(evilProv, 1);

        SecretKeyFactory pbkdf2 =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1",
                        System.getProperty("test.provider.name", "SunJCE"));
        PBEKeySpec pbks = new PBEKeySpec(PASS, SALT, ITER, 160);

        SecretKey secKey1 = pbkdf2.generateSecret(pbks);
        System.out.println("PBKDF2WithHmacSHA1:\n" +
                    HexFormat.of().withUpperCase().formatHex(secKey1.getEncoded()));
        if (Arrays.equals(secKey1.getEncoded(), EXP_OUT)) {
            System.out.println("Test Vector Passed");
        } else {
            System.out.println("Test Vector Failed");
            System.out.println("Expected Output:\n" +
                    HexFormat.of().withUpperCase().formatHex(EXP_OUT));
            throw new RuntimeException();
        }
    }
}

