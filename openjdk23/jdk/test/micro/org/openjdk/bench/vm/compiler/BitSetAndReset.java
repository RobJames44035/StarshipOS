/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class BitSetAndReset {
    private static final int COUNT = 10_000;

    private static final long MASK63 = 0x8000_0000_0000_0000L;
    private static final long MASK31 = 0x0000_0000_8000_0000L;
    private static final long MASK15 = 0x0000_0000_0000_8000L;
    private static final long MASK00 = 0x0000_0000_0000_0001L;

    private long andq, orq;
    private boolean success = true;

    @TearDown(Level.Iteration)
    public void finish() {
        if (!success)
            throw new AssertionError("Failure while setting or clearing long vector bits!");
    }

    @Benchmark
    public void bitSet(Blackhole bh) {
        for (int i=0; i<COUNT; i++) {
            andq = MASK63 | MASK31 | MASK15 | MASK00;
            orq = 0;
            bh.consume(test63());
            bh.consume(test31());
            bh.consume(test15());
            bh.consume(test00());
            success &= andq == 0 && orq == (MASK63 | MASK31 | MASK15 | MASK00);
        }
    }

    private long test63() {
        andq &= ~MASK63;
        orq |= MASK63;
        return 0L;
    }
    private long test31() {
        andq &= ~MASK31;
        orq |= MASK31;
        return 0L;
    }
    private long test15() {
        andq &= ~MASK15;
        orq |= MASK15;
        return 0L;
    }
    private long test00() {
        andq &= ~MASK00;
        orq |= MASK00;
        return 0L;
    }

    private static final long MASK62 = 0x4000_0000_0000_0000L;
    private static final long MASK61 = 0x2000_0000_0000_0000L;
    private static final long MASK60 = 0x1000_0000_0000_0000L;

    private long orq63, orq62, orq61, orq60;

    @Benchmark
    public void throughput(Blackhole bh) {
        for (int i=0; i<COUNT; i++) {
            orq63 = orq62 = orq61 = orq60 = 0;
            bh.consume(testTp());
        }
    }

    private long testTp() {
        orq63 |= MASK63;
        orq62 |= MASK62;
        orq61 |= MASK61;
        orq60 |= MASK60;
        return 0L;
    }
}
