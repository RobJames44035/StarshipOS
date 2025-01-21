 /*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @bug 6425477
 * @summary Better support for generation of high entropy random numbers
 * @run main/othervm StrongSeedReader
 */

import java.io.*;
import java.net.*;
import java.security.SecureRandom;

/**
 * A simple test which takes into account knowledge about the underlying
 * implementation. This may change if the implementations change.
 *
 * Create a new EGD file with known bytes, then set the EGD System property. The
 * data read should be the same as what was written.
 */
public class StrongSeedReader {

    public static void main(String[] args) throws Exception {
        // Skip Windows, the SHA1PRNG uses CryptGenRandom.
        if (System.getProperty("os.name", "unknown").startsWith("Windows")) {
            return;
        }

        File file = null;
        try {
            file = new File(System.getProperty("java.io.tmpdir"),
                    "StrongSeedReader.tmpdata");

            // write a bunch of 0's to the file.
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(new byte[2048]);

            System.setProperty("java.security.egd", file.toURI().toString());
            testSeed("NativePRNG");
            testSeed("SHA1PRNG");
            testSeed("DRBG");
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }

    private static void testSeed(String alg) throws Exception {
        System.out.println("Testing: " + alg);
        SecureRandom sr = SecureRandom.getInstance(alg);
        byte[] ba = sr.generateSeed(20);

        // We should get back a bunch of zeros from the file.
        for (byte b : ba) {
            if (b != 0) {
                throw new Exception("Byte != 0");
            }
        }
    }
}
