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

import java.lang.invoke.MethodType;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark assesses the MethodType.changeReturnType()
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodTypeChangeReturn {

    private MethodType mt;

    @Setup
    public void setup() {
        mt = MethodType.methodType(void.class, int.class, int.class);
    }

    @Benchmark
    public MethodType baselineSame() {
        return mt.changeReturnType(void.class);
    }

    @Benchmark
    public MethodType testChange() {
        return mt.changeReturnType(Integer.class);
    }

}
