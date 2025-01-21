/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.TimeUnit;

import static java.lang.foreign.ValueLayout.JAVA_INT;

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
        generic = JAVA_INT.varHandle();
        exact = generic.withInvokeExactBehavior();
    }

    Arena arena;
    MemorySegment data;

    @Setup
    public void setup() {
        arena = Arena.ofConfined();
        data = arena.allocate(JAVA_INT);
    }

    @TearDown
    public void tearDown() {
        arena.close();
    }

    @Benchmark
    public void exact_exactInvocation() {
        exact.set(data, (long) 0, 42);
    }

    @Benchmark
    public void generic_genericInvocation() {
        generic.set(data, 0, 42);
    }

    @Benchmark
    public void generic_exactInvocation() {
        generic.set(data, (long) 0, 42);
    }
}
