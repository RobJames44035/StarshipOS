/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.java.lang.invoke;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
public class VarHandleExact {

    static final VarHandle exact;
    static final VarHandle generic;

    static {
        try {
            generic = MethodHandles.lookup().findVarHandle(Data.class, "longField", long.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        exact = generic.withInvokeExactBehavior();
    }

    Data data;

    static class Data {
        long longField;
    }

    @Setup
    public void setup() {
        data = new Data();
    }

    @Benchmark
    public void exact_exactInvocation() {
        exact.set(data, (long) 42);
    }

    @Benchmark
    public void generic_genericInvocation() {
        generic.set(data, 42);
    }

    @Benchmark
    public void generic_exactInvocation() {
        generic.set(data, (long) 42);
    }
}
