/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.lang;

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

import java.util.concurrent.TimeUnit;

/**
 * Benchmark measuring aligned System.arraycopy.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(value = 3)
public class ArrayCopyAligned {

    @Param({"1", "3", "5", "10", "20", "70", "150", "300", "600", "1200"})
    int length;

    int fromPos, toPos;
    byte[] fromByteArr, toByteArr;
    char[] fromCharArr, toCharArr;
    int[] fromIntArr, toIntArr;
    long[] fromLongArr, toLongArr;

    @Setup
    public void setup() {
        // Both positions aligned
        fromPos = 8;
        toPos = 8;

        fromByteArr = new byte[1210];
        toByteArr = new byte[1210];
        fromCharArr = new char[1210];
        toCharArr = new char[1210];
        fromIntArr = new int[1210];
        toIntArr = new int[1210];
        fromLongArr = new long[1210];
        toLongArr = new long[1210];
    }

    @Benchmark
    public void testByte() {
        System.arraycopy(fromByteArr, fromPos, toByteArr, toPos, length);
    }

    @Benchmark
    public void testChar() {
        System.arraycopy(fromCharArr, fromPos, toCharArr, toPos, length);
    }

    @Benchmark
    public void testInt() {
        System.arraycopy(fromIntArr, fromPos, toIntArr, toPos, length);
    }

    @Benchmark
    public void testLong() {
        System.arraycopy(fromLongArr, fromPos, toLongArr, toPos, length);
    }

}
