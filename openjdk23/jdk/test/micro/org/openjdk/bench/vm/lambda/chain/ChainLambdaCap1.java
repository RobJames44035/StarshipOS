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
public class ChainLambdaCap1 extends ChainBase {

    public Level start1;
    public Level start2;
    public Level start4;
    public Level start8;

    @Setup
    public void init() {
        start1 = get1("capture me");
        start2 = get2("capture me");
        start4 = get4("capture me");
        start8 = get8("capture me");
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

    public static TopLevel get0(String v) {
        return () -> "GOT: "+v;
    }

    public static Level get1(String v) {
        return () -> get0(v);
    }

    public static Level get2(String v) {
        return () -> get1(v);
    }

    public static Level get3(String v) {
        return () -> get2(v);
    }

    public static Level get4(String v) {
        return () -> get3(v);
    }

    public static Level get5(String v) {
        return () -> get4(v);
    }

    public static Level get6(String v) {
        return () -> get5(v);
    }

    public static Level get7(String v) {
        return () -> get6(v);
    }

    public static Level get8(String v) {
        return () -> get7(v);
    }

}
