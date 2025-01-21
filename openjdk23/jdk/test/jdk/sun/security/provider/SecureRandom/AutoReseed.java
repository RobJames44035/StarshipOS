/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.security.SecureRandom;
import java.security.Security;

/**
 * @test
 * @bug 8051408
 * @summary make sure nextBytes etc can be called before setSeed
 * @run main/othervm -Djava.security.egd=file:/dev/urandom AutoReseed
 */
public class AutoReseed {

    public static void main(String[] args) throws Exception {
        SecureRandom sr;
        boolean pass = true;
        for (String mech : new String[]{"Hash_DRBG", "HMAC_DRBG", "CTR_DRBG"}) {
            try {
                System.out.println("Testing " + mech + "...");
                Security.setProperty("securerandom.drbg.config", mech);

                // Check auto reseed works
                sr = SecureRandom.getInstance("DRBG");
                sr.nextInt();
                sr = SecureRandom.getInstance("DRBG");
                sr.reseed();
                sr = SecureRandom.getInstance("DRBG");
                sr.generateSeed(10);
            } catch (Exception e) {
                pass = false;
                e.printStackTrace(System.out);
            }
        }
        if (!pass) {
            throw new RuntimeException("At least one test case failed");
        }
    }
}
