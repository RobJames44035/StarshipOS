/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package org.openjdk.bench.vm.lang;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@State(value = Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class ThrowableRuntimeMicros {

    // TestStack will add this number of calls to the call stack
    @Param({"4", "100", "1000"})
    // For more thorough testing, consider:
    // @Param({"4", "10", "100", "256", "1000"})
    public int depth;

    /** Build a call stack of a given size, then run trigger code in it.
      * (Does not account for existing frames higher up in the JMH machinery).
      */
    static class TestStack {
        final long fence;
        long current;
        final Runnable trigger;

        TestStack(long max, Runnable trigger) {
            this.fence = max;
            this.current = 0;
            this.trigger = trigger;
        }

        public void start() {
            one();
        }

        public void one() {
            if (check()) {
                two();
            }
        }

        void two() {
            if (check()) {
                three();
            }
        }

        private void three() {
            if (check()) {
                one();
            }
        }

        boolean check() {
            if (++current == fence) {
                trigger.run();
                return false;
            } else {
                return true;
            }
        }
    }

    @Benchmark
    public void testThrowableInit(Blackhole bh) {
        final Blackhole localBH = bh;
        final boolean[] done = {false};
        new TestStack(depth, new Runnable() {
            public void run() {
                localBH.consume(new Throwable());
                done[0] = true;
            }
        }).start();
        if (!done[0]) {
            throw new RuntimeException();
        }
    }

    @Benchmark
    public void testThrowableGetStackTrace(Blackhole bh) {
        final Blackhole localBH = bh;
        final boolean[] done = {false};
        new TestStack(depth, new Runnable() {
            public void run() {
                localBH.consume(new Throwable().getStackTrace());
                done[0] = true;
            }
        }).start();
        if (!done[0]) {
            throw new RuntimeException();
        }
    }

    @Benchmark
    public void testThrowableSTEtoString(Blackhole bh) {
        final Blackhole localBH = bh;
        final boolean[] done = {false};
        new TestStack(depth, new Runnable() {
            public void run() {
                Throwable t = new Throwable();
                for (StackTraceElement ste : t.getStackTrace()) {
                    localBH.consume(ste.toString());
                }
                done[0] = true;
            }
        }).start();
        if (!done[0]) {
            throw new RuntimeException();
        }
    }
}
