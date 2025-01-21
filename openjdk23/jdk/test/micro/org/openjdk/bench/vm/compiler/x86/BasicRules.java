/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.vm.compiler.x86;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Fork(value = 3, jvmArgs = "-XX:-UseSuperWord")
@Warmup(time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
public class BasicRules {
    static final int[] INT_ARRAY = new int[1024];
    static final long[] LONG_ARRAY = new long[1024];
    static final int INT_IMM = 100;
    static final long LONG_IMM = 100;

    @Setup(Level.Iteration)
    public void setup() {
        Random random = new Random(1000);

        for (int i = 0; i < 1024; i++) {
            INT_ARRAY[i] = random.nextInt();
            LONG_ARRAY[i] = random.nextLong();
        }
    }

    @Benchmark
    public void andL_rReg_imm255(Blackhole bh) {
        for (int i = 0; i < LONG_ARRAY.length; i++) {
            long v = LONG_ARRAY[i];
            bh.consume(v);
            bh.consume(v & 0xFFL);
        }
    }

    @Benchmark
    public void andL_rReg_imm65535(Blackhole bh) {
        for (int i = 0; i < LONG_ARRAY.length; i++) {
            long v = LONG_ARRAY[i];
            bh.consume(v);
            bh.consume(v & 0xFFFFL);
        }
    }

    @Benchmark
    public void add_mem_con(Blackhole bh) {
        for (int i = 0; i < INT_ARRAY.length; i++) {
            bh.consume(INT_ARRAY[i] + 100);
        }
    }

    @Benchmark
    public void divL_10(Blackhole bh) {
        for (int i = 0; i < LONG_ARRAY.length; i++) {
            bh.consume(LONG_ARRAY[i] / 10L);
        }
    }

    @Benchmark
    public void salI_rReg_1(Blackhole bh) {
        for (int i = 0; i < INT_ARRAY.length; i++) {
            bh.consume(INT_ARRAY[i] << 1);
        }
    }

    @Benchmark
    public void sarI_rReg_1(Blackhole bh) {
        for (int i = 0; i < INT_ARRAY.length; i++) {
            bh.consume(INT_ARRAY[i] >> 1);
        }
    }

    @Benchmark
    public void shrI_rReg_1(Blackhole bh) {
        for (int i = 0; i < INT_ARRAY.length; i++) {
            bh.consume(INT_ARRAY[i] >>> 1);
        }
    }

    @Benchmark
    public void salL_rReg_1(Blackhole bh) {
        for (int i = 0; i < LONG_ARRAY.length; i++) {
            bh.consume(LONG_ARRAY[i] << 1);
        }
    }

    @Benchmark
    public void sarL_rReg_1(Blackhole bh) {
        for (int i = 0; i < LONG_ARRAY.length; i++) {
            bh.consume(LONG_ARRAY[i] >> 1);
        }
    }

    @Benchmark
    public void shrL_rReg_1(Blackhole bh) {
        for (int i = 0; i < LONG_ARRAY.length; i++) {
            bh.consume(LONG_ARRAY[i] >>> 1);
        }
    }

    @Benchmark
    public void subI_rReg_imm(Blackhole bh) {
        for (int i = 0; i < INT_ARRAY.length; i++) {
            bh.consume(INT_ARRAY[i] - INT_IMM);
        }
    }

    @Benchmark
    public void subL_rReg_imm(Blackhole bh) {
        for (int i = 0; i < LONG_ARRAY.length; i++) {
            bh.consume(LONG_ARRAY[i] - LONG_IMM);
        }
    }

    @Benchmark
    public void cmovL_imm_01(Blackhole bh) {
        for (int i = 0; i < INT_ARRAY.length; i++) {
            bh.consume(INT_ARRAY[i] > 0 ? 1L : 0L);
        }
    }
}

