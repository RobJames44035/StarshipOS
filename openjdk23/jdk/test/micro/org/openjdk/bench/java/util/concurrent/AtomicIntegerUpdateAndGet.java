/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.concurrent;

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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * Benchmarks assesses the performance of new Atomic* API.
 *
 * Implementation notes:
 *   - atomic instances are padded to eliminate false sharing
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
public class AtomicIntegerUpdateAndGet {

    private PaddedAtomicInteger count;
    private int value = 42;
    private IntUnaryOperator captureOp;
    private IntUnaryOperator noCaptureOp;

    @Setup
    public void setup() {
        count = new PaddedAtomicInteger();
        noCaptureOp = new IntUnaryOperator() {
            public int applyAsInt(int v) {
                return v + 42;
            }
        };
        captureOp = new IntUnaryOperator() {
            public int applyAsInt(int v) {
                return v + value;
            }
        };
    }

    @Benchmark
    public int testAddAndGet() {
        return count.addAndGet(42);
    }

    @Benchmark
    public int testInnerNoCapture() {
        return count.updateAndGet(new IntUnaryOperator() {
            public int applyAsInt(int v) {
                return v + 42;
            }
        });
    }

    @Benchmark
    public int testInnerCapture() {
        return count.updateAndGet(new IntUnaryOperator() {
            public int applyAsInt(int v) {
                return v + value;
            }
        });
    }

    @Benchmark
    public int testInnerCaptureCached() {
        return count.updateAndGet(captureOp);
    }

    @Benchmark
    public int testInnerNoCaptureCached() {
        return count.updateAndGet(noCaptureOp);
    }

    @Benchmark
    public int testLambdaNoCapture() {
        return count.updateAndGet(x -> x + 42);
    }

    @Benchmark
    public int testLambdaCapture() {
        return count.updateAndGet(x -> x + value);
    }

    private static class PaddedAtomicInteger extends AtomicInteger {
        private volatile long pad00, pad01, pad02, pad03, pad04, pad05, pad06, pad07;
        private volatile long pad10, pad11, pad12, pad13, pad14, pad15, pad16, pad17;
    }

}
