/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.lang.invoke;

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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark assesses the performance of MethodHandles.arrayElementSetter
 *
 * @author Aleksey Shipilev (aleksey.shipilev@oracle.com)
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodHandlesArrayElementSetter {

    /**
     * Implementation notes:
     *   - creating simple array, and accessing the middle element
     *   - might have done iteration over array, but that will measure pipelining effects instead
     *   - volatile modifier on array breaks the DCE, which would otherwise eliminate the array store
     *   - the array is not shared to prevent true sharing
     *   - the array is large enough to prevent false sharing
     */

    private static final int SIZE = 1024;
    private static final int POS = SIZE/2;

    private static MethodHandle mh;
    private volatile int[] array;

    @Setup
    public void setup() throws Throwable {
        array = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = i;
        }
        mh = MethodHandles.arrayElementSetter(int[].class);
    }

    @Benchmark
    public MethodHandle testCreate() {
        return MethodHandles.arrayElementSetter(int[].class);
    }

    @Benchmark
    public void baselineRaw() {
        access(array, POS, 1);
    }

    @Benchmark
    public void testSetter() throws Throwable {
        mh.invoke(array, POS, 1);
    }

    public void access(int[] array, int pos, int v) {
        array[pos] = v;
    }

}
