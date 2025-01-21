/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(3)
public class IfMinMax {
    private static final int SIZE = 10_000;

    @Benchmark
    public void testSingleInt(Blackhole blackhole, BenchState state) {
        int a = state.i1[state.random.nextInt(SIZE)];
        int b = state.i2[state.random.nextInt(SIZE)];
        blackhole.consume(a > b ? a : b);
    }

    @Benchmark
    public void testVectorInt(Blackhole blackhole, BenchState state) {
        for (int i = 0; i < SIZE; i++) {
            state.i3[i] = state.i1[i] > state.i2[i] ? state.i1[i] : state.i2[i];
        }

        blackhole.consume(state.i3);
    }

    @Benchmark
    public void testReductionInt(Blackhole blackhole, BenchState state) {
        int a = 0;

        for (int i = 0; i < SIZE; i++) {
            if (state.i1[i] > a) {
                a = state.i1[i];
            }
        }

        blackhole.consume(a);
    }

    @Benchmark
    public void testSingleLong(Blackhole blackhole, BenchState state) {
        long a = state.l1[state.random.nextInt(SIZE)];
        long b = state.l2[state.random.nextInt(SIZE)];
        blackhole.consume(a > b ? a : b);
    }

    @Benchmark
    public void testVectorLong(Blackhole blackhole, BenchState state) {
        for (int i = 0; i < SIZE; i++) {
            state.l3[i] = state.l1[i] > state.l2[i] ? state.l1[i] : state.l2[i];
        }

        blackhole.consume(state.l3);
    }

    @Benchmark
    public void testReductionLong(Blackhole blackhole, BenchState state) {
        long a = 0;

        for (int i = 0; i < SIZE; i++) {
            if (state.l1[i] > a) {
                a = state.l1[i];
            }
        }

        blackhole.consume(a);
    }

    @State(Scope.Benchmark)
    public static class BenchState {
        private final int[] i1 = new int[SIZE];
        private final int[] i2 = new int[SIZE];
        private final int[] i3 = new int[SIZE];

        private final long[] l1 = new long[SIZE];
        private final long[] l2 = new long[SIZE];
        private final long[] l3 = new long[SIZE];

        private Random random;

        public BenchState() {
        }

        @Setup
        public void setup() {
            this.random = new Random(1000);

            for (int i = 0; i < SIZE; i++) {
                i1[i] = this.random.nextInt();
                i2[i] = this.random.nextInt();
                i3[i] = this.random.nextInt();

                l1[i] = this.random.nextLong();
                l2[i] = this.random.nextLong();
                l3[i] = this.random.nextLong();
            }
        }
    }
}