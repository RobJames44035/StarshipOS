/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
public class FPComparison {
    static final int INVOCATIONS = 1024;

    float[] f1;
    double[] d1;
    float[] f2;
    double[] d2;
    int[] res;

    @Setup
    public void setup() {
        var random = RandomGenerator.getDefault();
        f1 = new float[INVOCATIONS];
        d1 = new double[INVOCATIONS];
        f2 = new float[INVOCATIONS];
        d2 = new double[INVOCATIONS];
        res = new int[INVOCATIONS];
        for (int i = 0; i < INVOCATIONS; i++) {
            int type = random.nextInt(5);
            if (type == 1) {
                f1[i] = random.nextFloat();
                d1[i] = random.nextDouble();
                f2[i] = random.nextFloat();
                d2[i] = random.nextDouble();
            } else if (type == 2) {
                f1[i] = Float.POSITIVE_INFINITY;
                d1[i] = Double.POSITIVE_INFINITY;
                f2[i] = Float.POSITIVE_INFINITY;
                d2[i] = Double.POSITIVE_INFINITY;
            } else if (type == 3) {
                f1[i] = Float.NEGATIVE_INFINITY;
                d1[i] = Double.NEGATIVE_INFINITY;
                f2[i] = Float.NEGATIVE_INFINITY;
                d2[i] = Double.NEGATIVE_INFINITY;
            } else if (type >= 4) {
                f1[i] = Float.NaN;
                d1[i] = Double.NaN;
                f2[i] = Float.NaN;
                d2[i] = Double.NaN;
            }
        }
    }

    @Benchmark
    public void isNanFloat() {
        for (int i = 0; i < INVOCATIONS; i++) {
            res[i] = Float.isNaN(f1[i]) ? 1 : 0;
        }
    }

    @Benchmark
    public void isNanDouble() {
        for (int i = 0; i < INVOCATIONS; i++) {
            res[i] = Double.isNaN(d1[i]) ? 1 : 0;
        }
    }

    @Benchmark
    public void isInfiniteFloat() {
        for (int i = 0; i < INVOCATIONS; i++) {
            res[i] = Float.isInfinite(f1[i]) ? 1 : 0;
        }
    }

    @Benchmark
    public void isInfiniteDouble() {
        for (int i = 0; i < INVOCATIONS; i++) {
            res[i] = Double.isInfinite(d1[i]) ? 1 : 0;
        }
    }

    @Benchmark
    public void isFiniteFloat() {
        for (int i = 0; i < INVOCATIONS; i++) {
            res[i] = Float.isFinite(f1[i]) ? 1 : 0;
        }
    }

    @Benchmark
    public void isFiniteDouble() {
        for (int i = 0; i < INVOCATIONS; i++) {
            res[i] = Double.isFinite(d1[i]) ? 1 : 0;
        }
    }

    @Benchmark
    public void equalFloat() {
        for (int i = 0; i < INVOCATIONS; i++) {
            res[i] = (f1[i] == f2[i]) ? 1 : 0;
        }
    }

    @Benchmark
    public void equalDouble() {
        for (int i = 0; i < INVOCATIONS; i++) {
            res[i] = (d1[i] == d2[i]) ? 1 : 0;
        }
    }
}
