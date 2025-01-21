/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Tests for Array.equals() with 80 entry arrays differing at beginning, middle, or end.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class ArraysEquals {

    public char[] testCharArray1 = "1234567890123456789012345678901234567890123456789012345678901234567890123456789a".toCharArray();
    public char[] testCharArray2 = "1234567890123456789012345678901234567890123456789012345678901234567890123456789b".toCharArray();
    public char[] testCharArray3 = "123456789012345678901234567890123456789a123456789012345678901234567890123456789b".toCharArray();
    public char[] testCharArray4 = "1234567890a2345678901234567890123456789a123456789012345678901234567890123456789b".toCharArray();
    public char[] testCharArray5 = "1234567890123456789012345678901234567890123456789012345678901234567890123456789a".toCharArray();
    public byte[] testByteArray1 = "1234567890123456789012345678901234567890123456789012345678901234567890123456789a".getBytes();
    public byte[] testByteArray2 = "1234567890123456789012345678901234567890123456789012345678901234567890123456789b".getBytes();
    public byte[] testByteArray3 = "123456789012345678901234567890123456789a123456789012345678901234567890123456789b".getBytes();
    public byte[] testByteArray4 = "1234567890a2345678901234567890123456789a123456789012345678901234567890123456789b".getBytes();
    public byte[] testByteArray5 = "1234567890123456789012345678901234567890123456789012345678901234567890123456789a".getBytes();

    /** Char array tests */

    @Benchmark
    public boolean testCharTrue() {
        return Arrays.equals(testCharArray1, testCharArray5);
    }

    @Benchmark
    public boolean testCharFalseEnd() {
        return Arrays.equals(testCharArray1, testCharArray2);
    }

    @Benchmark
    public boolean testCharFalseMid() {
        return Arrays.equals(testCharArray1, testCharArray3);
    }

    @Benchmark
    public boolean testCharFalseBeginning() {
        return Arrays.equals(testCharArray1, testCharArray4);
    }

    /** Byte arrays tests */
    @Benchmark
    public boolean testByteTrue() {
        return Arrays.equals(testByteArray1, testByteArray5);
    }

    @Benchmark
    public boolean testByteFalseEnd() {
        return Arrays.equals(testByteArray1, testByteArray2);
    }

    @Benchmark
    public boolean testByteFalseMid() {
        return Arrays.equals(testByteArray1, testByteArray3);
    }

    @Benchmark
    public boolean testByteFalseBeginning() {
        return Arrays.equals(testByteArray1, testByteArray4);
    }
}
