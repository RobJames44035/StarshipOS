/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(3)
public class MulNodeIdealize {
    private static final int SIZE = 50;

    @Benchmark
    public void testMul2Float(Blackhole blackhole) {
        for (float i = 0; i < SIZE; i++) {
            blackhole.consume(i * 2);
        }
    }

    @Benchmark
    public void testMul2Double(Blackhole blackhole) {
        for (double i = 0; i < SIZE; i++) {
            blackhole.consume(i * 2);
        }
    }
}
