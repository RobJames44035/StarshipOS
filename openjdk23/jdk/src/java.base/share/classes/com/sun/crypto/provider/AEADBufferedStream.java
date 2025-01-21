/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package com.sun.crypto.provider;

import jdk.internal.util.ArraysSupport;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HexFormat;

/**
 * This class extends ByteArrayOutputStream by optimizing internal buffering.
 * It skips bounds checking, as the buffers are known and input previously
 * checked.  toByteArray() returns the internal buffer to avoid an extra copy.
 *
 * This uses `count` to determine the state of `buf`.  `buf` can still
 * point to an array while `count` equals zero.
 */
final class AEADBufferedStream extends ByteArrayOutputStream {

    /**
     * Create an instance with the specified buffer
     */

    public AEADBufferedStream(int len) {
        super(len);
    }

    /**
     * This method saves memory by returning the internal buffer. The calling
     * method must use {@code size()} for the relevant data length as the
     * returning byte[] maybe larger.
     *
     * @return internal buffer.
     */
    public byte[] getBuffer() {
        return buf;
    }

    /**
     * This method with expand the buffer if {@code count} + {@code len}
     * is larger than the buffer byte[] length.
     * @param len length to add to the current buffer
     */
    private void checkCapacity(int len) {
        int blen = buf.length;
        // Create a new larger buffer and append the new data
        if (blen < count + len) {
            buf = Arrays.copyOf(buf, ArraysSupport.newLength(blen, len, blen));
        }
    }

    /**
     * Takes a ByteBuffer writing non-blocksize data directly to the internal
     * buffer.
     * @param src remaining non-blocksize ByteBuffer
     */
    public void write(ByteBuffer src) {
        int pos = src.position();
        int len = src.remaining();

        if (src.hasArray()) {
            write(src.array(), pos + src.arrayOffset(), len);
            src.position(pos + len);
            return;
        }

        checkCapacity(len);
        src.get(buf, count, len);
        count += len;
    }

    @Override
    public void write(byte[] in, int offset, int len) {
        checkCapacity(len);
        System.arraycopy(in, offset, buf, count, len);
        count += len;
    }

    @Override
    public String toString() {
        return (count == 0 ? "null" : HexFormat.of().formatHex(buf));
    }
}
