/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class EnumHashCode {

    enum E {
        A, B, C;
    }

    Enum e = E.A;

    @Benchmark
    public int constant() {
        // Tests that hash code is constant-foldable
        return E.A.hashCode();
    }

    @Benchmark
    public int field() {
        // Tests that hash code is efficient
        return e.hashCode();
    }

}
