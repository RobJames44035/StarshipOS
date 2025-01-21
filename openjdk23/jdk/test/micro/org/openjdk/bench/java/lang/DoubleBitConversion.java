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
public class DoubleBitConversion {

    double doubleZero = 0;
    double doubleOne = 1;
    double doubleNan = Double.NaN;

    long longDoubleZero = Double.doubleToLongBits(0);
    long longDoubleOne = Double.doubleToLongBits(1);
    long longDoubleNaN = Double.doubleToLongBits(Double.NaN);

    @Benchmark
    public long doubleToRawLongBits_zero() {
        return Double.doubleToRawLongBits(doubleZero);
    }

    @Benchmark
    public long doubleToRawLongBits_one() {
        return Double.doubleToRawLongBits(doubleOne);
    }

    @Benchmark
    public long doubleToRawLongBits_NaN() {
        return Double.doubleToRawLongBits(doubleNan);
    }

    @Benchmark
    public long doubleToLongBits_zero() {
        return Double.doubleToLongBits(doubleZero);
    }

    @Benchmark
    public long doubleToLongBits_one() {
        return Double.doubleToLongBits(doubleOne);
    }

    @Benchmark
    public long doubleToLongBits_NaN() {
        return Double.doubleToLongBits(doubleNan);
    }

    @Benchmark
    public double longBitsToDouble_zero() {
        return Double.longBitsToDouble(longDoubleZero);
    }

    @Benchmark
    public double longBitsToDouble_one() {
        return Double.longBitsToDouble(longDoubleOne);
    }

    @Benchmark
    public double longBitsToDouble_NaN() {
        return Double.longBitsToDouble(longDoubleNaN);
    }

}

