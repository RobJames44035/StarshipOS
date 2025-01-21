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
public class TestEor3 {
    @Param({"2048"})
    private int LENGTH;

    private int[] ia;
    private int[] ib;
    private int[] ic;
    private int[] id;

    private long[] la;
    private long[] lb;
    private long[] lc;
    private long[] ld;

    @Param("0")
    private int seed;
    private Random random = new Random(seed);

    @Setup
    public void init() {
        ia = new int[LENGTH];
        ib = new int[LENGTH];
        ic = new int[LENGTH];
        id = new int[LENGTH];

        la = new long[LENGTH];
        lb = new long[LENGTH];
        lc = new long[LENGTH];
        ld = new long[LENGTH];

        for (int i = 0; i < LENGTH; i++) {
            ia[i] = random.nextInt();
            ib[i] = random.nextInt();
            ic[i] = random.nextInt();

            la[i] = random.nextLong();
            lb[i] = random.nextLong();
            lc[i] = random.nextLong();
        }
    }

    // Test EOR3 for int arrays
    @Benchmark
    public void test1Int() {
        for (int i = 0; i < LENGTH; i++) {
            id[i] = ia[i] ^ ib[i] ^ ic[i];
        }
    }

    // Test EOR3 for int arrays with multiple eor operations
    @Benchmark
    public void test2Int() {
        for (int i = 0; i < LENGTH; i++) {
            id[i] = ia[i] ^ ib[i] ^ ic[i] ^ ia[i] ^ ib[i];
        }
    }

    // Test EOR3 for long arrays
    @Benchmark
    public void test1Long() {
        for (int i = 0; i < LENGTH; i++) {
            ld[i] = la[i] ^ lb[i] ^ lc[i];
        }
    }

    // Test EOR3 for long arrays with multiple eor operations
    @Benchmark
    public void test2Long() {
        for (int i = 0; i < LENGTH; i++) {
            ld[i] = la[i] ^ lb[i] ^ lc[i] ^ la[i] ^ lb[i];
        }
    }
}
