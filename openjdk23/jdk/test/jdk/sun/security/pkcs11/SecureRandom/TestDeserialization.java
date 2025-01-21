/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6837847
 * @summary Ensure a deserialized PKCS#11 SecureRandom is functional.
 * @library /test/lib ..
 * @modules jdk.crypto.cryptoki
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;

public class TestDeserialization extends PKCS11Test {

    public void main(Provider p) throws Exception {
        // Skip this test for providers not found by java.security.Security
        if (Security.getProvider(p.getName()) != p) {
            System.out.println("Skip test for provider " + p.getName());
            return;
        }
        SecureRandom r;
        try {
            r = SecureRandom.getInstance("PKCS11", p);
            System.out.println("SecureRandom instance " + r);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Provider " + p +
                               " does not support SecureRandom, skipping");
            e.printStackTrace();
            return;
        }
        r.setSeed(System.currentTimeMillis());
        byte[] buf = new byte[16];
        byte[] ser = toByteArray(r);
        System.out.println("Serialized Len = " + ser.length);
        SecureRandom r2 = fromByteArray(ser);
        System.out.println("Deserialized into " + r2);
        r2.nextBytes(buf);
        System.out.println("Done");
    }

    public static void main(String[] args) throws Exception {
        main(new TestDeserialization());
    }

    private byte[] toByteArray(SecureRandom r) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        ObjectOutputStream outStream = null;
        try {
            outStream = new ObjectOutputStream(out);
            outStream.writeObject(r);
            return out.toByteArray();
        } finally {
            if (outStream != null) {
                outStream.close();
            }
        }
    }

    private SecureRandom fromByteArray(byte[] buf) throws Exception {
        SecureRandom r = null;
        ByteArrayInputStream is = new ByteArrayInputStream(buf);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(is);
            r = (SecureRandom) ois.readObject();
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
        return r;
    }
}
