/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test 1.1, 03/08/13
 * @bug 4532506 8301126
 * @summary Serializing KeyPair on one VM (Sun),
 *      and Deserializing on another (IBM) fails
 */

import java.io.*;

public class SerialOld {
    public static void main(String[] args) throws Exception {

        // verify tiger DSA and RSA public keys still deserialize in our VM

        deserializeTigerKey("DSA");
        deserializeTigerKey("RSA");

        // verify pre-tiger keys still deserialize in our VM.

        // There used to be a RSA test here, but the serialized file contained
        // classes introduced in JDK 5.0 (sun.security.rsa.RSA*).  The older
        // RSA keys from JDK 1.4.2 were of class JSA_* which were removed when
        // sun.security.rsa was introduced.  (See JDK-8301126 for more
        // details.)  The test/data has been removed.

        deserializeKey("DSA");
        deserializeKey("DH");
        deserializeKey("AES");
        deserializeKey("Blowfish");
        deserializeKey("DES");
        deserializeKey("DESede");
        deserializeKey("RC5");
        deserializeKey("HmacSHA1");
        deserializeKey("HmacMD5");
        deserializeKey("PBE");
    }

    private static void deserializeTigerKey(String algorithm) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream
                        (System.getProperty("test.src", ".") +
                        File.separator +
                        algorithm + ".1.5.key"));
        ois.readObject();
        ois.close();
    }
    private static void deserializeKey(String algorithm) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream
                        (System.getProperty("test.src", ".") +
                        File.separator +
                        algorithm + ".pre.1.5.key"));
        ois.readObject();
        ois.close();
    }
}
