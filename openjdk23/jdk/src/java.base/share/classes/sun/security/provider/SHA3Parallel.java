/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package sun.security.provider;

import jdk.internal.vm.annotation.IntrinsicCandidate;

import java.security.InvalidAlgorithmParameterException;
import java.util.Arrays;

import static sun.security.provider.ByteArrayAccess.b2lLittle;
import static sun.security.provider.ByteArrayAccess.l2bLittle;

import static sun.security.provider.SHA3.keccak;

public class SHA3Parallel {
    private int blockSize = 0;
    private static final int DM = 5; // dimension of lanesArr
    private byte[][] buffers;
    private long[][] lanesArr;
    private static final int NRPAR = 2;

    private SHA3Parallel(byte[][] buffers, int blockSize) throws InvalidAlgorithmParameterException {
        if ((buffers.length != NRPAR) || (buffers[0].length < blockSize)) {
            throw new InvalidAlgorithmParameterException("Bad buffersize.");
        }
        this.buffers = buffers;
        this.blockSize = blockSize;
        lanesArr = new long[NRPAR][];
        for (int i = 0; i < NRPAR; i++) {
            lanesArr[i] = new long[DM * DM];
            b2lLittle(buffers[i], 0, lanesArr[i], 0, blockSize);
        }
    }

    public void reset(byte[][] buffers) throws InvalidAlgorithmParameterException {
        if ((buffers.length != NRPAR) || (buffers[0].length < blockSize)) {
            throw new InvalidAlgorithmParameterException("Bad buffersize.");
        }
        this.buffers = buffers;
        for (int i = 0; i < NRPAR; i++) {
            Arrays.fill(lanesArr[i], 0L);
            b2lLittle(buffers[i], 0, lanesArr[i], 0, blockSize);
        }
    }

    public int squeezeBlock() {
        int retVal = doubleKeccak(lanesArr[0], lanesArr[1]);
        for (int i = 0; i < NRPAR; i++) {
            l2bLittle(lanesArr[i], 0, buffers[i], 0, blockSize);
        }
        return retVal;
    }

    @IntrinsicCandidate
    private static int doubleKeccak(long[] lanes0, long[] lanes1) {
        doubleKeccakJava(lanes0, lanes1);
        return 1;
    }

    private static int doubleKeccakJava(long[] lanes0, long[] lanes1) {
        keccak(lanes0);
        keccak(lanes1);
        return 1;
    }

    public static final class Shake128Parallel extends SHA3Parallel {
        public Shake128Parallel(byte[][] buf) throws InvalidAlgorithmParameterException {
            super(buf, 168);
        }
    }
}
