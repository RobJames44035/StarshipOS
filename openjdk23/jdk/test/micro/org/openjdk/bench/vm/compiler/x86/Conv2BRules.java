/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.vm.compiler.x86;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 4, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(3)
public class Conv2BRules {
    @Benchmark
    public void testNotEquals0(Blackhole blackhole, BenchState state) {
        for (int i = 0; i < 128; i++) {
            int j = state.ints[i];
            blackhole.consume(j != 0);
        }
    }

    @Benchmark
    public void testEquals0(Blackhole blackhole, BenchState state) {
        for (int i = 0; i < 128; i++) {
            int j = state.ints[i];
            blackhole.consume(j == 0);
        }
    }

    @Benchmark
    public void testEquals1(Blackhole blackhole, BenchState state) {
        for (int i = 0; i < 128; i++) {
            int j = state.ints[i];
            blackhole.consume(j == 1);
        }
    }

    @Benchmark
    public void testNotEqualsNull(Blackhole blackhole, BenchState state) {
        for (int i = 0; i < 128; i++) {
            Object o = state.objs[i];
            blackhole.consume(o != null);
        }
    }

    @Benchmark
    public void testEqualsNull(Blackhole blackhole, BenchState state) {
        for (int i = 0; i < 128; i++) {
            Object o = state.objs[i];
            blackhole.consume(o == null);
        }
    }

    @State(Scope.Benchmark)
    public static class BenchState {
        int[] ints;
        Object[] objs;

        public BenchState() {

        }

        @Setup(Level.Iteration)
        public void setup() {
            Random random = new Random(1000);
            ints = new int[128];
            objs = new Object[128];
            for (int i = 0; i < 128; i++) {
                ints[i] = random.nextInt(3);
                objs[i] = random.nextInt(3) == 0 ? null : new Object();
            }
        }
    }
}
