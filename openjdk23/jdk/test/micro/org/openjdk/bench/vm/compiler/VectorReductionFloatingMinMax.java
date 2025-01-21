/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.*;

import java.util.concurrent.TimeUnit;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class VectorReductionFloatingMinMax {
    @Param({"512"})
    public int COUNT_DOUBLE;

    @Param({"3"})
    public int COUNT_FLOAT;

    private float[]  floatsA;
    private float[]  floatsB;
    private double[] doublesA;
    private double[] doublesB;

    @Param("0")
    private int seed;
    private Random r = new Random(seed);

    @Setup
    public void init() {
        floatsA = new float[COUNT_FLOAT];
        floatsB = new float[COUNT_FLOAT];
        doublesA = new double[COUNT_DOUBLE];
        doublesB = new double[COUNT_DOUBLE];

        for (int i = 0; i < COUNT_FLOAT; i++) {
            floatsA[i] = r.nextFloat();
            floatsB[i] = r.nextFloat();
        }
        for (int i = 0; i < COUNT_DOUBLE; i++) {
            doublesA[i] = r.nextDouble();
            doublesB[i] = r.nextDouble();
        }
    }

    @Benchmark
    @Fork(jvmArgs = {"-XX:-SuperWordLoopUnrollAnalysis"})
    public void maxRedF(Blackhole bh) {
        float max = 0.0f;
        for (int i = 0; i < COUNT_FLOAT; i++) {
            max = Math.max(max, Math.abs(floatsA[i] - floatsB[i]));
        }
        bh.consume(max);
    }

    @Benchmark
    @Fork(jvmArgs = {"-XX:-SuperWordLoopUnrollAnalysis"})
    public void minRedF(Blackhole bh) {
        float min = 0.0f;
        for (int i = 0; i < COUNT_FLOAT; i++) {
            min = Math.min(min, Math.abs(floatsA[i] - floatsB[i]));
        }
        bh.consume(min);
    }

    @Benchmark
    public void maxRedD(Blackhole bh) {
        double max = 0.0d;
        for (int i = 0; i < COUNT_DOUBLE; i++) {
            max = Math.max(max, Math.abs(doublesA[i] - doublesB[i]));
        }
        bh.consume(max);
    }

    @Benchmark
    public void minRedD(Blackhole bh) {
        double min = 0.0d;
        for (int i = 0; i < COUNT_DOUBLE; i++) {
            min = Math.min(min, Math.abs(doublesA[i] - doublesB[i]));
        }
        bh.consume(min);
    }
}
