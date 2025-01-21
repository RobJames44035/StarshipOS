/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8012900
 * @library ../UTIL
 * @build TestUtil
 * @run main TestCICOWithGCMAndAAD
 * @summary Test CipherInputStream/OutputStream with AES GCM mode with AAD.
 * @author Valerie Peng
 * @key randomness
 */
import java.io.*;
import java.security.*;
import java.util.*;
import javax.crypto.*;

public class TestCICOWithGCMAndAAD {
    public static void main(String[] args) throws Exception {
        //init Secret Key
        KeyGenerator kg = KeyGenerator.getInstance("AES",
                System.getProperty("test.provider.name", "SunJCE"));
        kg.init(128);
        SecretKey key = kg.generateKey();

        //Do initialization of the plainText
        byte[] plainText = new byte[700];
        Random rdm = new Random();
        rdm.nextBytes(plainText);

        byte[] aad = new byte[128];
        rdm.nextBytes(aad);
        byte[] aad2 = aad.clone();
        aad2[50]++;

        Cipher encCipher = Cipher.getInstance("AES/GCM/NoPadding",
                System.getProperty("test.provider.name", "SunJCE"));
        encCipher.init(Cipher.ENCRYPT_MODE, key);
        encCipher.updateAAD(aad);
        Cipher decCipher = Cipher.getInstance("AES/GCM/NoPadding",
                System.getProperty("test.provider.name", "SunJCE"));
        decCipher.init(Cipher.DECRYPT_MODE, key, encCipher.getParameters());
        decCipher.updateAAD(aad);

        byte[] recovered = test(encCipher, decCipher, plainText);
        if (!Arrays.equals(plainText, recovered)) {
            throw new Exception("sameAAD: diff check failed!");
        } else System.out.println("sameAAD: passed");

        encCipher.init(Cipher.ENCRYPT_MODE, key);
        encCipher.updateAAD(aad2);
        recovered = test(encCipher, decCipher, plainText);
        if (recovered != null && recovered.length != 0) {
            throw new Exception("diffAAD: no data should be returned!");
        } else System.out.println("diffAAD: passed");
   }

   private static byte[] test(Cipher encCipher, Cipher decCipher, byte[] plainText)
            throws Exception {
        //init cipher streams
        ByteArrayInputStream baInput = new ByteArrayInputStream(plainText);
        CipherInputStream ciInput = new CipherInputStream(baInput, encCipher);
        ByteArrayOutputStream baOutput = new ByteArrayOutputStream();
        CipherOutputStream ciOutput = new CipherOutputStream(baOutput, decCipher);

        //do test
        byte[] buffer = new byte[200];
        int len = ciInput.read(buffer);
        System.out.println("read " + len + " bytes from input buffer");

        while (len != -1) {
            ciOutput.write(buffer, 0, len);
            System.out.println("wite " + len + " bytes to output buffer");
            len = ciInput.read(buffer);
            if (len != -1) {
                System.out.println("read " + len + " bytes from input buffer");
            } else {
                System.out.println("finished reading");
            }
        }

        ciOutput.flush();
        ciInput.close();
        ciOutput.close();

        return baOutput.toByteArray();
    }
}
