/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.internal.jimage.decompressor;

import java.io.IOException;
import java.util.zip.Inflater;

/**
 *
 * ZIP Decompressor
 *
 * @implNote This class needs to maintain JDK 8 source compatibility.
 *
 * It is used internally in the JDK to implement jimage/jrtfs access,
 * but also compiled and delivered as part of the jrtfs.jar to support access
 * to the jimage file provided by the shipped JDK by tools running on JDK 8.
 */
final class ZipDecompressor implements ResourceDecompressor {

    @Override
    public String getName() {
        return ZipDecompressorFactory.NAME;
    }

    static byte[] decompress(byte[] bytesIn, int offset, long originalSize) throws Exception {
        if (originalSize > Integer.MAX_VALUE) {
            throw new OutOfMemoryError("Required array size too large");
        }
        byte[] bytesOut = new byte[(int) originalSize];

        Inflater inflater = new Inflater();
        inflater.setInput(bytesIn, offset, bytesIn.length - offset);

        int count = 0;
        while (!inflater.finished() && count < originalSize) {
            count += inflater.inflate(bytesOut, count, bytesOut.length - count);
        }

        inflater.end();

        if (count != originalSize) {
            throw new IOException("Resource content size mismatch");
        }

        return bytesOut;
    }

    @Override
    public byte[] decompress(StringsProvider reader, byte[] content, int offset,
            long originalSize) throws Exception {
        byte[] decompressed = decompress(content, offset, originalSize);
        return decompressed;
    }
}
