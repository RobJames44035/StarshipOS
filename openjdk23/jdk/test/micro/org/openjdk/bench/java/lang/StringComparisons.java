/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/*
 * This benchmark naively explores String::startsWith and other String
 * comparison methods
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(value = 3)
public class StringComparisons {

    @Param({"6", "15", "1024"})
    public int size;

    @Param({"true", "false"})
    public boolean utf16;

    public String string;
    public String equalString;
    public String endsWithA;
    public String endsWithB;
    public String startsWithA;

    @Setup
    public void setup() {
        String c = utf16 ? "\uff11" : "c";
        string = c.repeat(size);
        equalString = c.repeat(size);
        endsWithA = c.repeat(size).concat("A");
        endsWithB = c.repeat(size).concat("B");
        startsWithA = "A" + (c.repeat(size));
    }

    @Benchmark
    public boolean startsWith() {
        return endsWithA.startsWith(string);
    }

    @Benchmark
    public boolean endsWith() {
        return startsWithA.endsWith(string);
    }

    @Benchmark
    public boolean regionMatches() {
        return endsWithA.regionMatches(0, endsWithB, 0, endsWithB.length());
    }

    @Benchmark
    public boolean regionMatchesRange() {
        return startsWithA.regionMatches(1, endsWithB, 0, endsWithB.length() - 1);
    }

    @Benchmark
    public boolean regionMatchesCI() {
        return endsWithA.regionMatches(true, 0, endsWithB, 0, endsWithB.length());
    }
}
