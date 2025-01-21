/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/* @test
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @run main TestAvailable
 * @bug 7031075 8161426
 * @summary Make sure that available() method behaves as expected.
 * @key randomness
 */

import java.io.*;
import java.util.Random;
import java.util.zip.*;
import jdk.test.lib.RandomFactory;

public class TestAvailable {

    public static void main(String args[]) throws Throwable {
        Random r = RandomFactory.getRandom();
        for (int n = 0; n < 10; n++) {
            byte[] src = new byte[r.nextInt(100) + 1];
            r.nextBytes(src);
            // test InflaterInputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (DeflaterOutputStream dos = new DeflaterOutputStream(baos)) {
                dos.write(src);
            }
            try (InflaterInputStream iis = new InflaterInputStream(
                   new ByteArrayInputStream(baos.toByteArray()))) {
                test(iis, src);
            }

            // test GZIPInputStream
            baos = new ByteArrayOutputStream();
            try (GZIPOutputStream dos = new GZIPOutputStream(baos)) {
                dos.write(src);
            }
            try (GZIPInputStream gis = new GZIPInputStream(
                   new ByteArrayInputStream(baos.toByteArray()))) {
                test(gis, src);
            }
        }
    }

    private static void test(InputStream is, byte[] expected) throws IOException {
        int cnt = 0;
        do {
            int available = is.available();
            if (available > 0) {
                int b = is.read();
                if (b == -1) {
                    throw new RuntimeException("available() > 0, read() == -1 : failed!");
                }
                if (expected[cnt++] != (byte)b) {
                    throw new RuntimeException("read() : failed!");
                }
            } else if (available == 0) {
                if (is.read() != -1) {
                    throw new RuntimeException("available() == 0, read() != -1 : failed!");
                }
                break;
            } else {
                throw new RuntimeException("available() < 0 : failed!");
            }
        } while (true);
        if (cnt != expected.length) {
            throw new RuntimeException("read : failed!");
        }
    }

}
