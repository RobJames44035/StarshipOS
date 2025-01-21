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

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(3)
public class StringOther {

    private String testString;
    private Random rnd;

    @Setup
    public void setup() {
        testString = "Idealism is what precedes experience; cynicism is what follows.";
        rnd = new Random();
    }

    @Benchmark
    public void charAt(Blackhole bh) {
        for (int i = 0; i < testString.length(); i++) {
            bh.consume(testString.charAt(i));
        }
    }

    /**
     * Creates (hopefully) unique Strings and internizes them, creating a zillion forgettable strings in the JVMs string
     * pool.
     * <p/>
     * This will test 1.) The data structure/whatever for getting and adding Strings to intern table. 2.) The
     * intern-caches (java) behaviour on negative lookup (the string is new) 3.) GC's handling of weak handles. Since
     * every gc we must process and pretty much kill a zillion interned strings that are now not referenced anymore, the
     * majority of GC time will be spent in handle processing. So we get a picture of how well the pathological case of
     * this goes.
     */
    @Benchmark
    public String internUnique() {
        return String.valueOf(rnd.nextInt()).intern();
    }

}
