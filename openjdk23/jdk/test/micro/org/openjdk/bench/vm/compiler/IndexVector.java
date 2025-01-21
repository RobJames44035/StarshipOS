/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package org.openjdk.bench.vm.compiler;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class IndexVector {
    @Param({"65536"})
    private int count;

    private int[] idx;
    private int[] src;
    private int[] dst;
    private float[] f;

    @Setup
    public void init() {
        idx = new int[count];
        src = new int[count];
        dst = new int[count];
        f = new float[count];
        Random ran = new Random(0);
        for (int i = 0; i < count; i++) {
            src[i] = ran.nextInt();
        }
    }

    @Benchmark
    public void indexArrayFill() {
        for (int i = 0; i < count; i++) {
            idx[i] = i;
        }
    }

    @Benchmark
    public void exprWithIndex1() {
        for (int i = 0; i < count; i++) {
            dst[i] = src[i] * (i & 7);
        }
    }

    @Benchmark
    public void exprWithIndex2() {
        for (int i = 0; i < count; i++) {
            f[i] = i * i + 100;
        }
    }
}

