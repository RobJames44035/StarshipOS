/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.vm.gc;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class Alloc {

    public static final int LENGTH = 400;
    public static final int largeConstLen = 100;
    public static final int smallConstLen = 6;
    public int largeVariableLen = 100;
    public int smallVariableLen = 6;

    @Benchmark
    public void testLargeConstArray(Blackhole bh) throws Exception {
        int localArrlen = largeConstLen;
        for (int i = 0; i < LENGTH; i++) {
            Object[] tmp = new Object[localArrlen];
            bh.consume(tmp);
        }
    }

    @Benchmark
    public void testLargeVariableArray(Blackhole bh) throws Exception {
        int localArrlen = largeVariableLen;
        for (int i = 0; i < LENGTH; i++) {
            Object[] tmp = new Object[localArrlen];
            bh.consume(tmp);
        }
    }

    @Benchmark
    public void testSmallConstArray(Blackhole bh) throws Exception {
        int localArrlen = smallConstLen;
        for (int i = 0; i < LENGTH; i++) {
            Object[] tmp = new Object[localArrlen];
            bh.consume(tmp);
        }
    }

    @Benchmark
    public void testSmallObject(Blackhole bh) throws Exception {
        FortyBytes localDummy = null;
        for (int i = 0; i < LENGTH; i++) {
            FortyBytes tmp = new FortyBytes();
            tmp.next = localDummy;
            localDummy = tmp;
            bh.consume(tmp);
        }
    }

    @Benchmark
    public void testSmallVariableArray(Blackhole bh) throws Exception {
        int localArrlen = smallVariableLen;
        for (int i = 0; i < LENGTH; i++) {
            Object[] tmp = new Object[localArrlen];
            bh.consume(tmp);
        }
    }

    final class FortyBytes {
        Object next;
        int y, z, k, f, g, e, t;
    }

}
