/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package org.openjdk.bench.java.math;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(value = 3)
public class BigDecimalStripTrailingZeros {

    private BigDecimal xsPow, sPow, mPow, lPow, xlPow;

    @Setup
    public void setup() {
        xsPow = new BigDecimal(BigInteger.TEN.pow(1 << 4));
        sPow = new BigDecimal(BigInteger.TEN.pow(1 << 5));
        mPow = new BigDecimal(BigInteger.TEN.pow(1 << 10));
        lPow = new BigDecimal(BigInteger.TEN.pow(1 << 15));
        xlPow = new BigDecimal(BigInteger.TEN.pow(1 << 20));
    }

    /** Test BigDecimal.stripTrailingZeros() with 10^16  */
    @Benchmark
    @OperationsPerInvocation(1)
    public void testXS(Blackhole bh) {
        bh.consume(xsPow.stripTrailingZeros());
    }

    /** Test BigDecimal.stripTrailingZeros() with 10^32 */
    @Benchmark
    @OperationsPerInvocation(1)
    public void testS(Blackhole bh) {
        bh.consume(sPow.stripTrailingZeros());
    }

    /** Test BigDecimal.stripTrailingZeros() with 10^1024 */
    @Benchmark
    @OperationsPerInvocation(1)
    public void testM(Blackhole bh) {
        bh.consume(mPow.stripTrailingZeros());
    }

    /** Test BigDecimal.stripTrailingZeros() with 10^32_768 */
    @Benchmark
    @OperationsPerInvocation(1)
    public void testL(Blackhole bh) {
        bh.consume(lPow.stripTrailingZeros());
    }

    /** Test BigDecimal.stripTrailingZeros() with 10^1_048_576 */
    @Benchmark
    @OperationsPerInvocation(1)
    public void testXL(Blackhole bh) {
        bh.consume(xlPow.stripTrailingZeros());
    }
}
