/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.BitSet;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(3)
public class FloatBitConversion {

    float floatZero = 0;
    float floatOne = 1;
    float floatNan = Float.NaN;

    int intFloatZero = Float.floatToIntBits(0);
    int intFloatOne = Float.floatToIntBits(1);
    int intFloatNaN = Float.floatToIntBits(Float.NaN);

    @Benchmark
    public int floatToRawIntBits_zero() {
        return Float.floatToRawIntBits(floatZero);
    }

    @Benchmark
    public int floatToRawIntBits_one() {
        return Float.floatToRawIntBits(floatOne);
    }

    @Benchmark
    public int floatToRawIntBits_NaN() {
        return Float.floatToRawIntBits(floatNan);
    }

    @Benchmark
    public int floatToIntBits_zero() {
        return Float.floatToIntBits(floatZero);
    }

    @Benchmark
    public int floatToIntBits_one() {
        return Float.floatToIntBits(floatOne);
    }

    @Benchmark
    public int floatToIntBits_NaN() {
        return Float.floatToIntBits(floatNan);
    }

    @Benchmark
    public float intBitsToFloat_zero() {
        return Float.intBitsToFloat(intFloatZero);
    }

    @Benchmark
    public float intBitsToFloat_one() {
        return Float.intBitsToFloat(intFloatOne);
    }

    @Benchmark
    public float intBitsToFloat_NaN() {
        return Float.intBitsToFloat(intFloatNaN);
    }

}

