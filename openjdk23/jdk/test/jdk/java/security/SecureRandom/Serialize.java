/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
 * @bug 4102896
 * @summary Make sure that a SecureRandom object can be serialized
 * @run main/othervm Serialize
 */

import java.security.*;
import java.io.*;

public class Serialize {

    public static void main(String args[]) throws Exception {
        System.setProperty("java.security.egd", "file:/dev/urandom");

        for (String alg: new String[]{
                "SHA1PRNG", "DRBG", "Hash_DRBG", "HMAC_DRBG", "CTR_DRBG",
                "Hash_DRBG,SHA-512,192,pr_and_reseed"}) {

            System.out.println("Testing " + alg);
            SecureRandom s1;

            // A SecureRandom can be s11ned and des11ned at any time.

            // Brand new.
            s1 = getInstance(alg);
            revive(s1).nextInt();
            if (alg.contains("DRBG")) {
                revive(s1).reseed();
            }

            // Used
            s1 = getInstance(alg);
            s1.nextInt();    // state set
            revive(s1).nextInt();
            if (alg.contains("DRBG")) {
                revive(s1).reseed();
            }

            // Automatically reseeded
            s1 = getInstance(alg);
            if (alg.contains("DRBG")) {
                s1.reseed();
            }
            revive(s1).nextInt();
            if (alg.contains("DRBG")) {
                revive(s1).reseed();
            }

            // Manually seeded
            s1 = getInstance(alg);
            s1.setSeed(1L);
            revive(s1).nextInt();
            if (alg.contains("DRBG")) {
                revive(s1).reseed();
            }
        }
    }

    private static SecureRandom getInstance(String alg) throws Exception {
        if (alg.equals("SHA1PRNG") || alg.equals("DRBG")) {
            return SecureRandom.getInstance(alg);
        } else {
            String old = Security.getProperty("securerandom.drbg.config");
            try {
                Security.setProperty("securerandom.drbg.config", alg);
                return SecureRandom.getInstance("DRBG");
            } finally {
                Security.setProperty("securerandom.drbg.config", old);
            }
        }
    }

    private static SecureRandom revive(SecureRandom oldOne) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        new ObjectOutputStream(bout).writeObject(oldOne);
        SecureRandom newOne = (SecureRandom) new ObjectInputStream(
                new ByteArrayInputStream(bout.toByteArray())).readObject();
        if (!oldOne.toString().equals(newOne.toString())) {
            throw new Exception(newOne + " is not " + oldOne);
        }
        return newOne;
    }
}
