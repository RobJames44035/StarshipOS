/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.lang;

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
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Tests java.lang.Class.forName() with various inputs.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
public class ClassForName {

    private String aName, bName, cName;

    @Setup
    public void setup() {
        aName = A.class.getName();
        bName = B.class.getName();
        cName = C.class.getName();
    }

    /** Calls Class.forName with the same name over and over again. The class asked for exists. */
    @Benchmark
    public void test1(Blackhole bh) throws ClassNotFoundException {
        bh.consume(Class.forName(aName));
    }

    /** Calls Class.forName with the three different names over and over again. All classes asked for exist. */
    @Benchmark
    public void test3(Blackhole bh) throws ClassNotFoundException {
        bh.consume(Class.forName(aName));
        bh.consume(Class.forName(bName));
        bh.consume(Class.forName(cName));
    }

    static class A {}
    static class B {}
    static class C {}
}
