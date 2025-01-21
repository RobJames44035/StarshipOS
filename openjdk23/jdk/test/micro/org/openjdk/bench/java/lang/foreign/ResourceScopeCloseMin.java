/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.java.lang.foreign;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3)
public class ResourceScopeCloseMin {

    Runnable dummy = () -> {};

    static class ConfinedScope {
        final Thread owner;
        boolean closed;
        final List<Runnable> resources = new ArrayList<>();

        private void checkState() {
            if (closed) {
                throw new AssertionError("Closed");
            } else if (owner != Thread.currentThread()) {
                throw new AssertionError("Wrong thread");
            }
        }

        ConfinedScope() {
            this.owner = Thread.currentThread();
        }

        void addCloseAction(Runnable runnable) {
            checkState();
            resources.add(runnable);
        }

        public void close() {
            checkState();
            closed = true;
            for (Runnable r : resources) {
                r.run();
            }
        }
    }

    @Benchmark
    public void confined_close() {
        ConfinedScope scope = new ConfinedScope();
        try { // simulate TWR
            scope.addCloseAction(dummy);
            scope.close();
        } catch (RuntimeException ex) {
            scope.close();
            throw ex;
        }
    }

    @Benchmark
    public void confined_close_notry() {
        ConfinedScope scope = new ConfinedScope();
        scope.addCloseAction(dummy);
        scope.close();
    }
}
