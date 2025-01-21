/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package org.openjdk.bench.java.util;

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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class ArraysFill {

    @Param({"16", "31", "250", "266", "511", "2047", "2048", "8195"})
    public int size;

    public byte[] testByteArray;
    public char[] testCharArray;
    public short[] testShortArray;
    public int[] testIntArray;
    public long[] testLongArray;
    public float[] testFloatArray;
    public double[] testDoubleArray;

    @Setup
    public void setup() {
        testByteArray = new byte[size];
        testCharArray = new char[size];
        testShortArray = new short[size];
        testIntArray = new int[size];
        testLongArray = new long[size];
        testFloatArray = new float[size];
        testDoubleArray = new double[size];

    }

    @Benchmark
    public void testCharFill() {
        Arrays.fill(testCharArray, (char) -1);
    }

    @Benchmark
    public void testByteFill() {
        Arrays.fill(testByteArray, (byte) -1);
    }

    @Benchmark
    public void testShortFill() {
        Arrays.fill(testShortArray, (short) -1);
    }

    @Benchmark
    public void testIntFill() {
        Arrays.fill(testIntArray, -1);
    }

    @Benchmark
    public void testLongFill() {
        Arrays.fill(testLongArray, -1);
    }

    @Benchmark
    public void testFloatFill() {
        Arrays.fill(testFloatArray, (float) -1.0);
    }

    @Benchmark
    public void testDoubleFill() {
        Arrays.fill(testDoubleArray, -1.0);
    }
}
