/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import jdk.test.lib.Asserts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * @test
 * @bug 6587699
 * @summary Document DigestInputStream behavior when skip() or mark() / reset() is used
 * @library /test/lib
 */

public class TestSkipAndReset {
    public static void main(String[] args) throws Exception {
        byte[] data = "1234567890".getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(data), md);

        dis.read(); // read 1
        dis.read(new byte[2], 0, 1); // read 2
        dis.skip(3); // skip 3 4 5
        dis.mark(10); // remember 5
        dis.read(new byte[2]); // read 6 7
        dis.skip(1); // skip 8
        dis.read(); // read 9
        dis.reset(); // back to 5
        dis.readNBytes(3); // read 6 7 8
        dis.reset(); // back to 5
        dis.skip(2); // skip 6 7
        dis.readAllBytes(); // read 8 9 0
        dis.reset(); // back to 5
        dis.skip(3); // skip 6 7 8
        dis.transferTo(new ByteArrayOutputStream()); // read 9 0

        byte[] hash = md.digest();
        byte[] directHash = md.digest("1267967889090".getBytes(StandardCharsets.UTF_8));

        Asserts.assertTrue(MessageDigest.isEqual(hash, directHash));
    }
}
