/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/* @test
 * @bug 7036144
 * @summary Test concatenated gz streams when available() returns zero
 * @run junit GZIPInputStreamAvailable
 */

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GZIPInputStreamAvailable {

    public static final int NUM_COPIES = 100;

    @Test
    public void testZeroAvailable() throws IOException {

        // Create some uncompressed data and then repeat it NUM_COPIES times
        byte[] uncompressed1 = "this is a test".getBytes("ASCII");
        byte[] uncompressedN = repeat(uncompressed1, NUM_COPIES);

        // Compress the original data and then repeat that NUM_COPIES times
        byte[] compressed1 = deflate(uncompressed1);
        byte[] compressedN = repeat(compressed1, NUM_COPIES);

        // (a) Read back inflated data from a stream where available() is accurate and verify
        byte[] readback1 = inflate(new ByteArrayInputStream(compressedN));
        assertArrayEquals(uncompressedN, readback1);

        // (b) Read back inflated data from a stream where available() always returns zero and verify
        byte[] readback2 = inflate(new ZeroAvailableStream(new ByteArrayInputStream(compressedN)));
        assertArrayEquals(uncompressedN, readback2);
    }

    public static byte[] repeat(byte[] data, int count) {
        byte[] repeat = new byte[data.length * count];
        int off = 0;
        for (int i = 0; i < count; i++) {
            System.arraycopy(data, 0, repeat, off, data.length);
            off += data.length;
        }
        return repeat;
    }

    public static byte[] deflate(byte[] data) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (GZIPOutputStream out = new GZIPOutputStream(buf)) {
            out.write(data);
        }
        return buf.toByteArray();
    }

    public static byte[] inflate(InputStream in) throws IOException {
        return new GZIPInputStream(in).readAllBytes();
    }

    public static class ZeroAvailableStream extends FilterInputStream {
        public ZeroAvailableStream(InputStream in) {
            super(in);
        }
        @Override
        public int available() {
            return 0;
        }
    }
}
