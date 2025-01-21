/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package org.openjdk.bench.java.lang.ref;

import java.lang.ref.Cleaner;
import java.lang.ref.Cleaner.Cleanable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3, jvmArgs = {"-Xmx256m", "-Xms256m", "-XX:+AlwaysPreTouch"})
public class CleanerChurn {

    @Param({"128", "256", "512", "1024", "2048"})
    int recipFreq;

    @Benchmark
    public Object test() {
        boolean register = ThreadLocalRandom.current().nextInt(recipFreq) == 0;
        return new Target(register);
    }

    static class Target {
        private static final Cleaner CLEANER = Cleaner.create();
        public Target(boolean register) {
            if (register) {
                CLEANER.register(this, () -> {});
            }
        }
    }

}
