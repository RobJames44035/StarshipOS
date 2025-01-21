/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark measuring the gain of removing array bound checks in various cases
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class ArrayBoundCheckRemoval {

    private int[] a;
    private int j, u, v;

    @Setup
    public void setup() {
        a = new int[200];
    }

    @Benchmark
    public int[] testForLoopAccess() throws Exception {
        int[] a = this.a;
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        return a;
    }

    @Benchmark
    public int[] testBubblesortStripped() throws Exception {
        int[] a = this.a;
        int limit = a.length;
        int st = -1;

        while (st < limit) {
            st++;
            limit--;
            for (j = st; j < limit; j++) {
                u = a[j];
                v = a[j + 1];
            }
        }
        return a;
    }

    @Benchmark
    public int[] testBubblesort() throws Exception {
        int[] a = this.a;
        int j1;
        int limit = a.length;
        int st = -1;
        while (st < limit) {
            boolean flipped = false;
            st++;
            limit--;
            for (j1 = st; j1 < limit; j1++) {
                if (a[j1] > a[j1 + 1]) {
                    int T = a[j1];
                    a[j1] = a[j1 + 1];
                    a[j1 + 1] = T;
                    flipped = true;
                }
            }
            if (!flipped) {
                return a;
            }
            flipped = false;
            for (j1 = limit; --j1 >= st; ) {
                if (a[j1] > a[j1 + 1]) {
                    int T = a[j1];
                    a[j1] = a[j1 + 1];
                    a[j1 + 1] = T;
                    flipped = true;
                }
            }
            if (!flipped) {
                return a;
            }
        }
        return a;
    }

}
