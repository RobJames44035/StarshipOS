/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.*;

import java.util.concurrent.TimeUnit;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
public abstract class VectorLoadToStoreForwarding {
    @Param({"2048"})
    public int SIZE;

    private int[] aI;

    @Param("0")
    private int seed;
    private Random r = new Random(seed);

    @Setup
    public void init() {
        aI = new int[SIZE];

        for (int i = 0; i < SIZE; i++) {
            aI[i] = r.nextInt();
        }
    }

    @Benchmark
    public void benchmark_00() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 0] + 1;
        }
    }

    @Benchmark
    public void benchmark_01() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 1] + 1;
        }
    }

    @Benchmark
    public void benchmark_02() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 2] + 1;
        }
    }

    @Benchmark
    public void benchmark_03() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 3] + 1;
        }
    }

    @Benchmark
    public void benchmark_04() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 4] + 1;
        }
    }

    @Benchmark
    public void benchmark_05() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 5] + 1;
        }
    }

    @Benchmark
    public void benchmark_06() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 6] + 1;
        }
    }

    @Benchmark
    public void benchmark_07() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 7] + 1;
        }
    }

    @Benchmark
    public void benchmark_08() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 8] + 1;
        }
    }

    @Benchmark
    public void benchmark_09() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 9] + 1;
        }
    }

    @Benchmark
    public void benchmark_10() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 10] + 1;
        }
    }

    @Benchmark
    public void benchmark_11() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 11] + 1;
        }
    }

    @Benchmark
    public void benchmark_12() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 12] + 1;
        }
    }

    @Benchmark
    public void benchmark_13() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 13] + 1;
        }
    }

    @Benchmark
    public void benchmark_14() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 14] + 1;
        }
    }

    @Benchmark
    public void benchmark_15() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 15] + 1;
        }
    }

    @Benchmark
    public void benchmark_16() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 16] + 1;
        }
    }

    @Benchmark
    public void benchmark_17() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 17] + 1;
        }
    }

    @Benchmark
    public void benchmark_18() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 18] + 1;
        }
    }

    @Benchmark
    public void benchmark_19() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 19] + 1;
        }
    }

    @Benchmark
    public void benchmark_20() {
        for (int i = 20; i < SIZE; i++) {
            aI[i] = aI[i - 20] + 1;
        }
    }

    @Fork(value = 1, jvmArgs = {
        "-XX:+UseSuperWord"
    })
    public static class VectorLoadToStoreForwardingSuperWord extends VectorLoadToStoreForwarding {}

    @Fork(value = 1, jvmArgs = {
        "-XX:-UseSuperWord"
    })
    public static class VectorLoadToStoreForwardingNoSuperWord extends VectorLoadToStoreForwarding {}
}
