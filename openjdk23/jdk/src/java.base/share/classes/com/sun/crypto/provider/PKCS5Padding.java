/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package com.sun.crypto.provider;

import javax.crypto.ShortBufferException;
import java.util.Arrays;

/**
 * This class implements padding as specified in the PKCS#5 standard.
 *
 * @author Gigi Ankeny
 *
 *
 * @see Padding
 */
final class PKCS5Padding implements Padding {

    private final int blockSize;

    PKCS5Padding(int blockSize) {
        this.blockSize = blockSize;
    }

    /**
     * Adds the given number of padding bytes to the data input.
     * The value of the padding bytes is determined
     * by the specific padding mechanism that implements this
     * interface.
     *
     * @param in the input buffer with the data to pad
     * @param off the offset in <code>in</code> where the padding bytes
     * are appended
     * @param len the number of padding bytes to add
     *
     * @exception ShortBufferException if <code>in</code> is too small to hold
     * the padding bytes
     */
    public void padWithLen(byte[] in, int off, int len)
        throws ShortBufferException
    {
        if (in == null)
            return;

        int idx = Math.addExact(off, len);
        if (idx > in.length) {
            throw new ShortBufferException("Buffer too small to hold padding");
        }

        byte paddingOctet = (byte) (len & 0xff);
        Arrays.fill(in, off, idx, paddingOctet);
    }

    /**
     * Returns the index where the padding starts.
     *
     * <p>Given a buffer with padded data, this method returns the
     * index where the padding starts.
     *
     * @param in the buffer with the padded data
     * @param off the offset in <code>in</code> where the padded data starts
     * @param len the length of the padded data
     *
     * @return the index where the padding starts, or -1 if the input is
     * not properly padded
     */
    public int unpad(byte[] in, int off, int len) {
        if ((in == null) ||
            (len == 0)) { // this can happen if input is really a padded buffer
            return 0;
        }
        int idx = Math.addExact(off, len);
        byte lastByte = in[idx - 1];
        int padValue = (int)lastByte & 0x0ff;
        if ((padValue < 0x01)
            || (padValue > blockSize)) {
            return -1;
        }

        int start = idx - padValue;
        if (start < off) {
            return -1;
        }

        for (int i = start; i < idx; i++) {
            if (in[i] != lastByte) {
                return -1;
            }
        }
        return start;
    }

    /**
     * Determines how long the padding will be for a given input length.
     *
     * @param len the length of the data to pad
     *
     * @return the length of the padding
     */
    public int padLength(int len) {
        return blockSize - (len % blockSize);
    }
}
