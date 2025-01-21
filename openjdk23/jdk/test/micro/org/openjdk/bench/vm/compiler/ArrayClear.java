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
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class ArrayClear {

    @Param("100000")
    private int arraySize;

    private int[] sourceIntArray;

    @Setup
    public void setupSubclass() {
        sourceIntArray = new int[arraySize];
        for (int i = 0; i < arraySize; i += 1) {
            sourceIntArray[i] = i;
        }
    }

    @Benchmark
    public int[] testArrayClear() throws Exception {
        int[] intArray = new int[arraySize];
        System.arraycopy(sourceIntArray, 0, intArray, 0, arraySize);
        return intArray;
    }
}
