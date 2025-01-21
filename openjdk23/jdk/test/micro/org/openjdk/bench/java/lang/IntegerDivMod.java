/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
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
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Warmup;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.concurrent.TimeUnit;

/**
 * Tests unsigned division and modulus methods in java.lang.Integer
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
public class IntegerDivMod {

    RandomGenerator randomGenerator;

    @Param({"mixed", "positive", "negative"})
    String divisorType;
    @Param({"1024"})
    int BUFFER_SIZE;
    int[] dividends, divisors, quotients, remainders;

    @Setup
    public void setup() {
        dividends = new int[BUFFER_SIZE];
        divisors = new int[BUFFER_SIZE];
        quotients =  new int[BUFFER_SIZE];
        remainders =  new int[BUFFER_SIZE];
        RandomGenerator rng = RandomGeneratorFactory.getDefault().create(0);
        for (int i = 0; i < BUFFER_SIZE; i++) {
            dividends[i] = rng.nextInt();
            int divisor = rng.nextInt();
            if (divisorType.equals("positive")) divisor = Math.abs(divisor);
            else if (divisorType.equals("negative")) divisor = -Math.abs(divisor);
            divisors[i] = divisor;
        }
    }

    @Benchmark
    public void testDivideUnsigned() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            quotients[i] = Integer.divideUnsigned(dividends[i], divisors[i]);
        }
    }

    @Benchmark
    public void testRemainderUnsigned() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            remainders[i] = Integer.remainderUnsigned(dividends[i], divisors[i]);
        }
    }

    @Benchmark
    public void testDivideRemainderUnsigned() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            divmod(dividends[i], divisors[i], i);
        }
    }

    public void divmod(int dividend, int divisor, int i) {
        quotients[i] = Integer.divideUnsigned(dividend, divisor);
        remainders[i] = Integer.remainderUnsigned(dividend, divisor);
    }

}



