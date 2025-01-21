/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package org.openjdk.bench.vm.compiler;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Fork(value = 3)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@CompilerControl(CompilerControl.Mode.DONT_INLINE)
public class Rotation {

    private static final int COUNT = 5000;

    @State(Scope.Benchmark)
    public static class MyState {
        public int xi = 10;
        public int yi = 24;
    }

    @Benchmark
    public void xorRotateRight(MyState s, Blackhole blackhole) {
        int x = s.xi;
        int y = s.yi;
        for (int i = 0; i < COUNT; i++) {
            y = x ^ ((y >>> 5) | (y << -5));
        }
        blackhole.consume(y);
    }

    @Benchmark
    public void bicRotateRight(MyState s, Blackhole blackhole) {
        int x = s.xi;
        int y = s.yi;
        for (int i = 0; i < COUNT; i++) {
            y = x & (-1 ^ ((y >>> 5) | (y << -5)));
        }
        blackhole.consume(y);
    }

    @Benchmark
    public void eonRotateRight(MyState s, Blackhole blackhole) {
        int x = s.xi;
        int y = s.yi;
        for (int i = 0; i < COUNT; i++) {
            y = x ^ (-1 ^ ((y >>> 5) | (y << -5)));
        }
        blackhole.consume(y);
    }

    @Benchmark
    public void ornRotateRight(MyState s, Blackhole blackhole) {
        int x = s.xi;
        int y = s.yi;
        for (int i = 0; i < COUNT; i++) {
            y = x | (-1 ^ ((y >>> 5) | (y << -5)));
        }
        blackhole.consume(y);
    }

    @Benchmark
    public void andRotateRight(MyState s, Blackhole blackhole) {
        int x = s.xi;
        int y = s.yi;
        for (int i = 0; i < COUNT; i++) {
            y = x & ((y >>> 5) | (y << -5));
        }
        blackhole.consume(y);
    }
}
