/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8001596
 * @modules java.base/javax.crypto.spec:open
 * @summary Incorrect condition check in PBKDF2KeyImpl.java
 */

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.lang.reflect.*;

public class NegativeLength {

    public static void main(String[] args) throws Exception {
        SecretKeyFactory skf = SecretKeyFactory.getInstance(
            "PBKDF2WithHmacSHA1",
                System.getProperty("test.provider.name", "SunJCE"));


        // Create a valid PBEKeySpec
        PBEKeySpec pbeks = new PBEKeySpec(
            new char['p'], new byte[1], 1024, 8);

        // Use reflection to set it negative.
        Class c = pbeks.getClass();
        Field f = c.getDeclaredField("keyLength");
        f.setAccessible(true);
        f.setInt(pbeks, -8);

        System.out.println("pbeks.getKeyLength(): " + pbeks.getKeyLength());

        try {

            // A negative length is clearly wrong, we should get a
            // InvalidKeySpecException.  Anything else is wrong.
            skf.generateSecret(pbeks);
            throw new Exception("We shouldn't get here.");
        } catch (InvalidKeySpecException ike) {
            // swallow, this is the exception we want.
            System.out.println("Test Passed.");
        }
    }
}

