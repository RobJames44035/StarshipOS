/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Thread)
public class StringSubstring {

    public String s = new String("An arbitrary string that happened to be of length 52");

    @Benchmark
    public String from26toEnd0() {
        return s.substring(26);
    }

    @Benchmark
    public String from26toEnd1() {
        return s.substring(26, s.length());
    }

    /**
     * An empty substring should not allocate a new String
     */
    @Benchmark
    public String empty() {
        return s.substring(17, 17);
    }
}
