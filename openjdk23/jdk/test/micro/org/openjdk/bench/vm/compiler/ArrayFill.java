/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package org.openjdk.bench.vm.compiler;

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
import java.util.Arrays;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class ArrayFill {
    @Param("65536") private int size;

    private byte[] ba;
    private short[] sa;
    private int[] ia;

    @Setup
    public void setup() {
        ba = new byte[size];
        sa = new short[size];
        ia = new int[size];
    }

    @Benchmark
    public void fillByteArray() {
        for (int i = 0; i < size; i++) {
            ba[i] = (byte) 123;
        }
    }

    @Benchmark
    public void fillShortArray() {
        for (int i = 0; i < size; i++) {
            sa[i] = (short) 12345;
        }
    }

    @Benchmark
    public void fillIntArray() {
        for (int i = 0; i < size; i++) {
            ia[i] = 1234567890;
        }
    }

    @Benchmark
    public void zeroByteArray() {
        for (int i = 0; i < size; i++) {
            ba[i] = 0;
        }
    }

    @Benchmark
    public void zeroShortArray() {
        for (int i = 0; i < size; i++) {
            sa[i] = 0;
        }
    }

    @Benchmark
    public void zeroIntArray() {
        for (int i = 0; i < size; i++) {
            ia[i] = 0;
        }
    }
}

