/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8151149
 * @modules java.base/javax.crypto:open
 *          java.base/com.sun.crypto.provider:+open
 */

import java.lang.reflect.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import com.sun.crypto.provider.*;

public class CheckPBEKeySize {

    private static final String ALGO = "PBEWithSHA1AndDESede";
    private static final int KEYSIZE = 112; // Triple DES effective key size

    public static final void main(String[] args) throws Exception {

        // Generate a PBE key
        SecretKeyFactory skFac = SecretKeyFactory.getInstance("PBE");
        SecretKey skey =
            skFac.generateSecret(new PBEKeySpec("test123".toCharArray()));

        // Initialize the PBE cipher
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, skey);

        // Permit access to the Cipher.spi field (a CipherSpi object)
        Field spi = Cipher.class.getDeclaredField("spi");
        spi.setAccessible(true);
        Object value = spi.get(cipher);

        // Permit access to the CipherSpi.engineGetKeySize method
        Method engineGetKeySize =
            PKCS12PBECipherCore$PBEWithSHA1AndDESede.class
                .getDeclaredMethod("engineGetKeySize", Key.class);
        engineGetKeySize.setAccessible(true);

        // Check the key size
        int keySize = (int) engineGetKeySize.invoke(value, skey);
        if (keySize == KEYSIZE) {
            System.out.println(ALGO + ".engineGetKeySize returns " + keySize +
                " bits, as expected");
            System.out.println("OK");
        } else {
            throw new Exception("ERROR: " + ALGO + " key size is incorrect");
        }
    }
}
