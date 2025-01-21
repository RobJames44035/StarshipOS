/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
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

/**
 * Tests speed of multiplication calculations with constants.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class Multiplication {

    @Param("500")
    private int arraySize;

    private long[] longArraySmall, longArrayBig;

    @Setup
    public void setupSubclass() {
        longArraySmall = new long[arraySize];
        longArrayBig = new long[arraySize];

        /*
         * small values always have higher 32 bits cleared. big values always
         * have higher 32 bits set.
         */
        for (int i = 0; i < arraySize; i++) {
            longArraySmall[i] = 100L * i + i;
            longArrayBig[i] = ((100L * i + i) << 32) + 4543 + i * 4;
        }
    }

    /* helper for small constant benchmarks. */
    private static long smallConstantHelper(long[] values) {
        long sum = 0;
        for (long value : values) {
            sum += value * 453543L;
        }
        return sum;
    }

    /* helper for big constant benchmarks. */
    private static long bigConstantHelper(long[] values) {
        long sum = 0;
        for (long value : values) {
            sum += value * 4554345533543L;
        }
        return sum;
    }

    /**
     * Test multiplications of longs. One of the operands is a small constant and the other is a variable that always is
     * small.
     */
    @Benchmark
    public long testLongSmallVariableSmallConstantMul() {
        return smallConstantHelper(longArraySmall);
    }

    /**
     * Test multiplications of longs. One of the operands is a big constant and the other is a variable that always is
     * small.
     */
    @Benchmark
    public long testLongSmallVariableBigConstantMul() {
        return bigConstantHelper(longArraySmall);
    }

    /**
     * Test multiplications of longs. One of the operands is a small constant and the other is a variable that always is
     * big.
     */
    @Benchmark
    public long testLongBigVariableSmallConstantMul() {
        return smallConstantHelper(longArrayBig);
    }

    /**
     * Test multiplications of longs. One of the operands is a big constant and the other is a variable that always is
     * big.
     */
    @Benchmark
    public long testLongBigVariableBigConstantMul() {
        return bigConstantHelper(longArrayBig);
    }

}
