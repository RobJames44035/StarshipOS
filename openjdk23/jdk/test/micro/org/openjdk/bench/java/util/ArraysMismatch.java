/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package org.openjdk.bench.java.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public abstract class ArraysMismatch {

    @Param({"90", "800"})
    private static int size;

    static final byte fill = 99;
    static final byte mismatch = -1;

    int leftStartRange;
    int leftEndRange;
    int rightStartRange;
    int rightEndRange;

    @Setup
    public void setup() {
        leftStartRange = size / 4;
        leftEndRange = size - size / 4;
        rightStartRange = size / 4 + 10;
        rightEndRange = size - size / 4 + 10;
        specificSetup();
    }

    abstract void specificSetup();

    public static class Byte extends ArraysMismatch {

        byte[] left;
        byte[] right_startMismatch;
        byte[] right_midMismatch;
        byte[] right_endMismatch;
        byte[] right_matches;

        public void specificSetup() {
            left = new byte[size];
            Arrays.fill(left, (byte) fill);
            right_startMismatch = Arrays.copyOf(left, left.length);
            right_startMismatch[2] = (byte) mismatch;
            right_midMismatch = Arrays.copyOf(left, left.length);
            right_midMismatch[size / 2] = (byte) mismatch;
            right_endMismatch = Arrays.copyOf(left, left.length);
            right_endMismatch[size - 5] = (byte) mismatch;
            right_matches = Arrays.copyOf(left, left.length);
        }

        @Benchmark
        public int matches() {
            return Arrays.mismatch(left, right_matches);
        }

        @Benchmark
        public int differentSubrangeMatches() {
            return Arrays.mismatch(left, leftStartRange, leftEndRange, right_matches, rightStartRange, rightEndRange);
        }

        @Benchmark
        public int mismatchEnd() {
            return Arrays.mismatch(left, right_endMismatch);
        }

        @Benchmark
        public int mismatchMid() {
            return Arrays.mismatch(left, right_midMismatch);
        }

        @Benchmark
        public int mismatchStart() {
            return Arrays.mismatch(left, right_startMismatch);
        }
    }

    public static class Char extends ArraysMismatch {

        char[] left;
        char[] right_startMismatch;
        char[] right_midMismatch;
        char[] right_endMismatch;
        char[] right_matches;

        public void specificSetup() {
            left = new char[size];
            Arrays.fill(left, (char) fill);
            right_startMismatch = Arrays.copyOf(left, left.length);
            right_startMismatch[2] = (char) mismatch;
            right_midMismatch = Arrays.copyOf(left, left.length);
            right_midMismatch[size / 2] = (char) mismatch;
            right_endMismatch = Arrays.copyOf(left, left.length);
            right_endMismatch[size - 5] = (char) mismatch;
            right_matches = Arrays.copyOf(left, left.length);
        }

        @Benchmark
        public int matches() {
            return Arrays.mismatch(left, right_matches);
        }

        @Benchmark
        public int differentSubrangeMatches() {
            return Arrays.mismatch(left, leftStartRange, leftEndRange, right_matches, rightStartRange, rightEndRange);
        }

        @Benchmark
        public int mismatchEnd() {
            return Arrays.mismatch(left, right_endMismatch);
        }

        @Benchmark
        public int mismatchMid() {
            return Arrays.mismatch(left, right_midMismatch);
        }

        @Benchmark
        public int mismatchStart() {
            return Arrays.mismatch(left, right_startMismatch);
        }
    }

    public static class Short extends ArraysMismatch {

        short[] left;
        short[] right_startMismatch;
        short[] right_midMismatch;
        short[] right_endMismatch;
        short[] right_matches;

        public void specificSetup() {
            left = new short[size];
            Arrays.fill(left, (short) fill);
            right_startMismatch = Arrays.copyOf(left, left.length);
            right_startMismatch[2] = (short) mismatch;
            right_midMismatch = Arrays.copyOf(left, left.length);
            right_midMismatch[size / 2] = (short) mismatch;
            right_endMismatch = Arrays.copyOf(left, left.length);
            right_endMismatch[size - 5] = (short) mismatch;
            right_matches = Arrays.copyOf(left, left.length);
        }

        @Benchmark
        public int matches() {
            return Arrays.mismatch(left, right_matches);
        }

        @Benchmark
        public int differentSubrangeMatches() {
            return Arrays.mismatch(left, leftStartRange, leftEndRange, right_matches, rightStartRange, rightEndRange);
        }

        @Benchmark
        public int mismatchEnd() {
            return Arrays.mismatch(left, right_endMismatch);
        }

        @Benchmark
        public int mismatchMid() {
            return Arrays.mismatch(left, right_midMismatch);
        }

        @Benchmark
        public int mismatchStart() {
            return Arrays.mismatch(left, right_startMismatch);
        }
    }

    public static class Int extends ArraysMismatch {

        int[] left;
        int[] right_startMismatch;
        int[] right_midMismatch;
        int[] right_endMismatch;
        int[] right_matches;

        public void specificSetup() {
            left = new int[size];
            Arrays.fill(left, (int) fill);
            right_startMismatch = Arrays.copyOf(left, left.length);
            right_startMismatch[2] = (int) mismatch;
            right_midMismatch = Arrays.copyOf(left, left.length);
            right_midMismatch[size / 2] = (int) mismatch;
            right_endMismatch = Arrays.copyOf(left, left.length);
            right_endMismatch[size - 5] = (int) mismatch;
            right_matches = Arrays.copyOf(left, left.length);
        }

        @Benchmark
        public int matches() {
            return Arrays.mismatch(left, right_matches);
        }

        @Benchmark
        public int differentSubrangeMatches() {
            return Arrays.mismatch(left, leftStartRange, leftEndRange, right_matches, rightStartRange, rightEndRange);
        }

        @Benchmark
        public int mismatchEnd() {
            return Arrays.mismatch(left, right_endMismatch);
        }

        @Benchmark
        public int mismatchMid() {
            return Arrays.mismatch(left, right_midMismatch);
        }

        @Benchmark
        public int mismatchStart() {
            return Arrays.mismatch(left, right_startMismatch);
        }
    }

    public static class Long extends ArraysMismatch {

        long[] left;
        long[] right_startMismatch;
        long[] right_midMismatch;
        long[] right_endMismatch;
        long[] right_matches;

        public void specificSetup() {
            left = new long[size];
            Arrays.fill(left, (long) fill);
            right_startMismatch = Arrays.copyOf(left, left.length);
            right_startMismatch[2] = (long) mismatch;
            right_midMismatch = Arrays.copyOf(left, left.length);
            right_midMismatch[size / 2] = (long) mismatch;
            right_endMismatch = Arrays.copyOf(left, left.length);
            right_endMismatch[size - 5] = (long) mismatch;
            right_matches = Arrays.copyOf(left, left.length);
        }

        @Benchmark
        public int matches() {
            return Arrays.mismatch(left, right_matches);
        }

        @Benchmark
        public int differentSubrangeMatches() {
            return Arrays.mismatch(left, leftStartRange, leftEndRange, right_matches, rightStartRange, rightEndRange);
        }

        @Benchmark
        public int mismatchEnd() {
            return Arrays.mismatch(left, right_endMismatch);
        }

        @Benchmark
        public int mismatchMid() {
            return Arrays.mismatch(left, right_midMismatch);
        }

        @Benchmark
        public int mismatchStart() {
            return Arrays.mismatch(left, right_startMismatch);
        }
    }

    public static class Float extends ArraysMismatch {

        float[] left;
        float[] right_startMismatch;
        float[] right_midMismatch;
        float[] right_endMismatch;
        float[] right_matches;

        public void specificSetup() {
            left = new float[size];
            Arrays.fill(left, (float) fill);
            right_startMismatch = Arrays.copyOf(left, left.length);
            right_startMismatch[2] = (float) mismatch;
            right_midMismatch = Arrays.copyOf(left, left.length);
            right_midMismatch[size / 2] = (float) mismatch;
            right_endMismatch = Arrays.copyOf(left, left.length);
            right_endMismatch[size - 5] = (float) mismatch;
            right_matches = Arrays.copyOf(left, left.length);
        }

        @Benchmark
        public int matches() {
            return Arrays.mismatch(left, right_matches);
        }

        @Benchmark
        public int differentSubrangeMatches() {
            return Arrays.mismatch(left, leftStartRange, leftEndRange, right_matches, rightStartRange, rightEndRange);
        }

        @Benchmark
        public int mismatchEnd() {
            return Arrays.mismatch(left, right_endMismatch);
        }

        @Benchmark
        public int mismatchMid() {
            return Arrays.mismatch(left, right_midMismatch);
        }

        @Benchmark
        public int mismatchStart() {
            return Arrays.mismatch(left, right_startMismatch);
        }
    }

    public static class Double extends ArraysMismatch {

        double[] left;
        double[] right_startMismatch;
        double[] right_midMismatch;
        double[] right_endMismatch;
        double[] right_matches;

        public void specificSetup() {
            left = new double[size];
            Arrays.fill(left, (double) fill);
            right_startMismatch = Arrays.copyOf(left, left.length);
            right_startMismatch[2] = (double) mismatch;
            right_midMismatch = Arrays.copyOf(left, left.length);
            right_midMismatch[size / 2] = (double) mismatch;
            right_endMismatch = Arrays.copyOf(left, left.length);
            right_endMismatch[size - 5] = (double) mismatch;
            right_matches = Arrays.copyOf(left, left.length);
        }

        @Benchmark
        public int matches() {
            return Arrays.mismatch(left, right_matches);
        }

        @Benchmark
        public int differentSubrangeMatches() {
            return Arrays.mismatch(left, leftStartRange, leftEndRange, right_matches, rightStartRange, rightEndRange);
        }

        @Benchmark
        public int mismatchEnd() {
            return Arrays.mismatch(left, right_endMismatch);
        }

        @Benchmark
        public int mismatchMid() {
            return Arrays.mismatch(left, right_midMismatch);
        }

        @Benchmark
        public int mismatchStart() {
            return Arrays.mismatch(left, right_startMismatch);
        }
    }

}
