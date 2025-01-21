/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
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
public class VectorIntMinMax {
    @Param({"2048"})
    private int LENGTH;

    private int[] ia;
    private int[] ib;
    private int[] ic;

    @Param("0")
    private int seed;
    private Random random = new Random(seed);

    @Setup
    public void init() {
        ia = new int[LENGTH];
        ib = new int[LENGTH];
        ic = new int[LENGTH];

        for (int i = 0; i < LENGTH; i++) {
            ia[i] = random.nextInt();
            ib[i] = random.nextInt();
        }
    }

    // Test Math.max for int arrays
    @Benchmark
    public void testMaxInt() {
        for (int i = 0; i < LENGTH; i++) {
            ic[i] = Math.max(ia[i], ib[i]);
        }
    }

    // Test Math.min for int arrays
    @Benchmark
    public void testMinInt() {
        for (int i = 0; i < LENGTH; i++) {
            ic[i] = Math.min(ia[i], ib[i]);
        }
    }

    // Test StrictMath.min for int arrays
    @Benchmark
    public void testStrictMinInt() {
        for (int i = 0; i < LENGTH; i++) {
            ic[i] = StrictMath.min(ia[i], ib[i]);
        }
    }

    // Test StrictMath.max for int arrays
    @Benchmark
    public void testStrictMaxInt() {
        for (int i = 0; i < LENGTH; i++) {
            ic[i] = StrictMath.max(ia[i], ib[i]);
        }
    }
}
