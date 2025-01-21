/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

/*
 * This benchmark naively explores String::equals performance
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(value = 3)
public class StringEquals {

    public String test = new String("0123456789");
    public String test2 = new String("tgntogjnrognagronagroangroarngorngaorng");
    public String test3 = new String(test); // equal to test, but not same
    public String test4 = new String("0123\u01FF");
    public String test5 = new String(test4); // equal to test4, but not same
    public String test6 = new String("0123456780");
    public String test7 = new String("0123\u01FE");

    @Benchmark
    public boolean different() {
        return test.equals(test2);
    }

    @Benchmark
    public boolean equal() {
        return test.equals(test3);
    }

    @Benchmark
    public boolean almostEqual() {
        return test.equals(test6);
    }

    @Benchmark
    public boolean almostEqualUTF16() {
        return test4.equals(test7);
    }

    @Benchmark
    public boolean differentCoders() {
        return test.equals(test4);
    }

    @Benchmark
    public boolean equalsUTF16() {
        return test5.equals(test4);
    }
}

