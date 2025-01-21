/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.java.util.random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Warmup;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.concurrent.TimeUnit;

/**
 * Tests java.util.random.RandomGenerator's different random number generators which use Math.unsignedMultiplyHigh().
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class RandomGeneratorNext {

    RandomGenerator randomGenerator;

    @Param({"L128X128MixRandom", "L128X256MixRandom", "L128X1024MixRandom"})
    String randomGeneratorName;

    long[] buffer;

    @Param("1024")
    int size;

    @Setup
    public void setup() {
        buffer = new long[size];
        randomGenerator = RandomGeneratorFactory.of(randomGeneratorName).create(randomGeneratorName.hashCode());
    }

    @Benchmark
    public long testNextLong() {
        return randomGenerator.nextLong();
    }

    @Benchmark
    public long[] testFillBufferWithNextLong() {
        for (int i = 0; i < size; i++) buffer[i] = randomGenerator.nextLong();
        return buffer;
    }

}
