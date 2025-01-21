/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
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
public class FloatingScalarVectorAbsDiff {
    @Param({"1024"})
    public int count;

    private float[]  floatsA,  floatsB,  floatsD;
    private double[] doublesA, doublesB, doublesD;

    @Param("316731")
    private int seed;
    private Random r = new Random(seed);

    @Setup
    public void init() {
        floatsA  = new float[count];
        doublesA = new double[count];

        floatsB  = new float[count];
        doublesB = new double[count];

        floatsD  = new float[count];
        doublesD = new double[count];

        for (int i = 0; i < count; i++) {
            floatsA[i]  = r.nextFloat();
            doublesB[i] = r.nextDouble();

            floatsB[i]  = r.nextFloat();
            doublesB[i] = r.nextDouble();
        }
    }

    @Benchmark
    public void testVectorAbsDiffFloat() {
        for (int i = 0; i < count; i++) {
            floatsD[i] = Math.abs(Math.abs(floatsA[i] - floatsB[i]) - 3.14f);
        }
    }

    @Benchmark
    public void testVectorAbsDiffDouble() {
        for (int i = 0; i < count; i++) {
            doublesD[i] = Math.abs(Math.abs(doublesA[i] - doublesB[i]) - 3.14d);
        }
    }

    @Benchmark
    public void testScalarAbsDiffFloat(Blackhole bh) {
        float a = r.nextFloat();
        float b = r.nextFloat();

        for (int i = 0; i < count; i++) {
            a = Math.abs(a - b);
            b = Math.abs(b - a);
        }

        bh.consume(a + b);
    }

    @Benchmark
    public void testScalarAbsDiffDouble(Blackhole bh) {
        double a = r.nextDouble();
        double b = r.nextDouble();

        for (int i = 0; i < count; i++) {
            a = Math.abs(a - b);
            b = Math.abs(b - a);
        }

        bh.consume(a + b);
    }
}
