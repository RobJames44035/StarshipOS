/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @test
 * @bug 8244706
 * @summary Verify that the OS header flag in the stream written out by java.util.zip.GZIPOutputStream
 * has the correct expected value
 * @run testng GZIPOutputStreamHeaderTest
 */
public class GZIPOutputStreamHeaderTest {

    private static final int OS_HEADER_INDEX = 9;
    private static final int HEADER_VALUE_OS_UNKNOWN = 255;

    /**
     * Test that the {@code OS} header field in the GZIP output stream
     * has a value of {@code 255} which represents "unknown"
     */
    @Test
    public void testOSHeader() throws Exception {
        final String data = "Hello world!!!";
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos);) {
            gzipOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
        }
        final byte[] compressed = baos.toByteArray();
        Assert.assertNotNull(compressed, "Compressed data is null");
        Assert.assertEquals(toUnsignedByte(compressed[OS_HEADER_INDEX]), HEADER_VALUE_OS_UNKNOWN,
                "Unexpected value for OS header");
        // finally verify that the compressed data is readable back to the original
        final String uncompressed;
        try (final ByteArrayOutputStream os = new ByteArrayOutputStream();
             final ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
             final GZIPInputStream gzipInputStream = new GZIPInputStream(bis)) {
            gzipInputStream.transferTo(os);
            uncompressed = new String(os.toByteArray(), StandardCharsets.UTF_8);
        }
        Assert.assertEquals(uncompressed, data, "Unexpected data read from GZIPInputStream");
    }

    private static int toUnsignedByte(final byte b) {
        return b & 0xff;
    }
}
