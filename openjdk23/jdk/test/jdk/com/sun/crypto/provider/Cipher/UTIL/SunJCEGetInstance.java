/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 7171982
 * @summary Test that SunJCE.getInstance() is retrieving a provider when
 * SunJCE has been removed from the provider list.
 * @run main/othervm SunJCEGetInstance
 */

import java.security.Security;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class SunJCEGetInstance {
    public static void main(String[] args) throws Exception {
        Cipher jce;

        try{
            String providerName = System.getProperty("test.provider.name", "SunJCE");
            // Remove SunJCE from Provider list
            Provider prov = Security.getProvider(providerName);
            Security.removeProvider(providerName);
            // Create our own instance of SunJCE provider.  Purposefully not
            // using SunJCE.getInstance() so we can have our own instance
            // for the test.
            jce = Cipher.getInstance("AES/CBC/PKCS5Padding", prov);

            jce.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec("1234567890abcedf".getBytes(), "AES"));
            jce.doFinal("PlainText".getBytes());
        } catch (Exception e) {
            System.err.println("Setup failure:  ");
            throw e;
        }

        // Get parameters which will call SunJCE.getInstance().  Failure
        // would occur on this line.
        try {
            jce.getParameters().getEncoded();

        } catch (Exception e) {
            System.err.println("Test Failure");
            throw e;
        }
        System.out.println("Passed");
    }
}
