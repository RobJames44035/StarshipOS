/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package org.openjdk.bench.java.util.regex;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Benchmarks of Patterns that exhibit O(2^N) performance due to catastrophic
 * backtracking, **when implemented naively**.
 *
 * See: jdk/test/java/util/regex/RegExTest.java#expoBacktracking
 * commit b45ea8903ec290ab194d9ebe040bc43edd5dd0a3
 * Author: Xueming Shen <sherman@openjdk.org>
 * Date:   Tue May 10 21:19:25 2016 -0700
 *
 * Here's a way to compare the per-char cost:
 *
 * (cd $(git rev-parse --show-toplevel) && for size in 16 128 1024; do make test TEST='micro:java.util.regex.Exponential' MICRO="FORK=1;WARMUP_ITER=1;ITER=4;OPTIONS=-opi $size -p size=$size" |& perl -ne 'print if /^Benchmark/ .. /^Finished running test/'; done)
 *
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(2)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Thread)
public class Exponential {
    /** Run length of non-matching consecutive whitespace chars. */
    @Param({"16", "128", "1024"})
    // 2048+ runs into StackOverflowError; see JDK-8260866
    int size;

    public String justXs;
    public String notJustXs;

    // Patterns that match justXs but not notJustXs
    public Pattern pat1;
    public Pattern pat2;
    public Pattern pat3;
    public Pattern pat4;

    Pattern compile(String regex) {
        Pattern pat = Pattern.compile(regex);
        // ad hoc correctness checking
        if (!  pat.matcher(justXs).matches()
            || pat.matcher(notJustXs).matches()) {
            throw new AssertionError("unexpected matching: " + regex);
        }
        return pat;
    }

    @Setup(Level.Trial)
    public void setup() {
        justXs = "X".repeat(size);
        notJustXs = justXs + "!";

        // Will (or should) the engine optimize (?:X|X) to X ?
        pat1 = compile("(?:X|X)*");

        // Tougher to optimize than pat1
        pat2 = compile("(?:[XY]|[XZ])*");

        pat3 = compile("(X+)+");

        pat4 = compile("^(X+)+$");
     }

    /** O(N) */
    @Benchmark
    public boolean pat1_justXs() {
        return pat1.matcher(justXs).matches();
    }

    /** O(N) */
    @Benchmark
    public boolean pat1_notJustXs() {
        return pat1.matcher(notJustXs).matches();
    }

    /** O(N) */
    @Benchmark
    public boolean pat2_justXs() {
        return pat2.matcher(justXs).matches();
    }

    /** O(N) */
    @Benchmark
    public boolean pat2_notJustXs() {
        return pat2.matcher(notJustXs).matches();
    }

    /** O(1) - very surprising! */
    @Benchmark
    public boolean pat3_justXs() {
        return pat3.matcher(justXs).matches();
    }

    /** O(N^2) - surprising!  O(N) seems very achievable. */
    @Benchmark
    public boolean pat3_notJustXs() {
        return pat3.matcher(notJustXs).matches();
    }

    /** O(1) - very surprising! */
    @Benchmark
    public boolean pat4_justXs() {
        return pat4.matcher(justXs).matches();
    }

    /** O(N^2) - surprising!  O(N) seems very achievable. */
    @Benchmark
    public boolean pat4_notJustXs() {
        return pat4.matcher(notJustXs).matches();
    }

}
