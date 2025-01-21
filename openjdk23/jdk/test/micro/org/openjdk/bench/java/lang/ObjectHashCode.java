/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * This benchmark assesses different hashCode strategies in HotSpot
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
public class ObjectHashCode {

    @Benchmark
    public int mode_default() {
        return System.identityHashCode(new Object());
    }

    // Experimental hashCode generation schemes. See synchronizer.cpp get_next_hash
    /*
    @Benchmark
    @Fork(jvmArgs = {"-XX:+UnlockExperimentalVMOptions", "-XX:hashCode=0"})
    public int mode_0() {
        return System.identityHashCode(new Object());
    }

    @Benchmark
    @Fork(jvmArgs = {"-XX:+UnlockExperimentalVMOptions", "-XX:hashCode=1"})
    public int mode_1() {
        return System.identityHashCode(new Object());
    }

    @Benchmark
    @Fork(jvmArgs = {"-XX:+UnlockExperimentalVMOptions", "-XX:hashCode=2"})
    public int mode_2() {
        return System.identityHashCode(new Object());
    }

    @Benchmark
    @Fork(jvmArgs = {"-XX:+UnlockExperimentalVMOptions", "-XX:hashCode=3"})
    public int mode_3() {
        return System.identityHashCode(new Object());
    }

    @Benchmark
    @Fork(jvmArgs = {"-XX:+UnlockExperimentalVMOptions", "-XX:hashCode=4"})
    public int mode_4() {
        return System.identityHashCode(new Object());
    }

    @Benchmark
    @Fork(jvmArgs = {"-XX:+UnlockExperimentalVMOptions", "-XX:hashCode=5"})
    public int mode_5() {
        return System.identityHashCode(new Object());
    }
    */

}
