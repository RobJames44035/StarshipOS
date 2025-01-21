/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 3, jvmArgs = { "--enable-native-access=ALL-UNNAMED" })
public class LoopOverOfAddress extends JavaLayouts {

    static final int ITERATIONS = 1_000_000;

    @Benchmark
    public long segment_loop_addr() {
        long res = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            res += MemorySegment.ofAddress(i % 100).address();
        }
        return res;
    }

    @Benchmark
    public long segment_loop_addr_size() {
        long res = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            res += MemorySegment.ofAddress(i)
                    .reinterpret(i % 100).address();
        }
        return res;
    }

    @Benchmark
    public long segment_loop_addr_size_session() {
        long res = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            res += MemorySegment.ofAddress(i)
                    .reinterpret(i % 100, Arena.global(), null).address();
        }
        return res;
    }
}
