/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package org.openjdk.bench.java.lang.ref;

import java.lang.ref.Cleaner;
import java.lang.ref.Cleaner.Cleanable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3, jvmArgs = {"-Xmx1g", "-Xms1g", "-XX:+AlwaysPreTouch"})
public class CleanerGC {

    @Param({"16384", "65536", "262144", "1048576", "4194304"})
    int count;

    // Make sure all targets are reachable and available for GC in scalable manner.
    // This exposes the potential GC problem in Cleaner lists.
    ArrayList<Target> targets;

    @Setup
    public void setup() {
        targets = new ArrayList<>();
        for (int c = 0; c < count; c++) {
            targets.add(new Target());
        }
    }

    @Benchmark
    public void test() {
        System.gc();
    }

    static class Target {
        private static final Cleaner CLEANER = Cleaner.create();
        public Target() {
            CLEANER.register(this, () -> {});
        }
    }

}
