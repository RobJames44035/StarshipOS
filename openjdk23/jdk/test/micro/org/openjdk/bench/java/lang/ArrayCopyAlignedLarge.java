/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
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
public class ArrayCopyAlignedLarge {

    @Param({"100000", "1000000", "2000000", "5000000", "10000000"})
    int length;

    int fromPos, toPos;
    byte[] fromByteArr, toByteArr;

    @Setup
    public void setup() {
        // Both positions aligned
        fromPos = 0;
        toPos = 0;

        fromByteArr = new byte[length];
        toByteArr = new byte[length];
    }

    @Benchmark
    public void testByte() {
        System.arraycopy(fromByteArr, fromPos, toByteArr, toPos, length);
    }
}
