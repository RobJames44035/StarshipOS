/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @summary Simple test to see if Strong or Unlimited Crypto Policy
 * files are installed.
 * @author Brad R. Wetmore
 */

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class StrongOrUnlimited {

    public static void main(String[] args) throws Exception {
        // decide if the installed jurisdiction policy file is the
        // unlimited version
        boolean isUnlimited = true;
        Cipher c = Cipher.getInstance("AES",
                System.getProperty("test.provider.name", "SunJCE"));

        try {
            c.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec(new byte[16], "AES"));
        } catch (InvalidKeyException ike) {
            throw new Exception("128 bit AES not available.");
        }

        try {
            c.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec(new byte[32], "AES"));
            System.out.println("Unlimited Crypto *IS* Installed");
        } catch (InvalidKeyException ike) {
            System.out.println("Unlimited Crypto *IS NOT* Installed");
        }
    }
}
