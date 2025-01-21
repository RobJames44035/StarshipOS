/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class Dates {

    int year = 75;
    int month = 11;
    int day = 23;
    int hours = 16;
    int minutes = 47;
    int seconds = 12;

    @Benchmark
    public Date testEmptyConstructor() {
        return new Date();
    }

    @SuppressWarnings("deprecation")
    @Benchmark
    public Date testIIIIIIConstructor() {
        hours++;
        minutes++;
        seconds++;
        return new Date(year, month, day, hours, minutes, seconds);
    }
}
