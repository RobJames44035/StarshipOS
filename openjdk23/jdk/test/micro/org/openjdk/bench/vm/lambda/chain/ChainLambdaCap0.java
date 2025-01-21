/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.vm.lambda.chain;
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
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Chain of (capture + invocation) microbenchmark.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class ChainLambdaCap0 extends ChainBase {

    public Level start1;
    public Level start2;
    public Level start4;
    public Level start8;

    @Setup
    public void init() {
        start1 = get1();
        start2 = get2();
        start4 = get4();
        start8 = get8();
    }

    @Benchmark
    @OperationsPerInvocation(1)
    public void call1(Blackhole bh) {
        process(bh, start1);
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void call2(Blackhole bh) {
        process(bh, start2);
    }

    @Benchmark
    @OperationsPerInvocation(4)
    public void call4(Blackhole bh) {
        process(bh, start4);
    }

    @Benchmark
    @OperationsPerInvocation(8)
    public void call8(Blackhole bh) {
        process(bh, start8);
    }

    public static TopLevel get0() {
        return () -> "GOT: ";
    }

    public static Level get1() {
        return () -> get0();
    }

    public static Level get2() {
        return () -> get1();
    }

    public static Level get3() {
        return () -> get2();
    }

    public static Level get4() {
        return () -> get3();
    }

    public static Level get5() {
        return () -> get4();
    }

    public static Level get6() {
        return () -> get5();
    }

    public static Level get7() {
        return () -> get6();
    }

    public static Level get8() {
        return () -> get7();
    }

}
