/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package org.openjdk.bench.vm.gc.systemgc;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@Fork(value=25, jvmArgs={"-Xmx5g", "-Xms5g", "-Xmn3g", "-XX:+AlwaysPreTouch"})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class OneBigObject {

    /*
     * Test the System GC when there is a single large object.
     *
     * The heap settings provided are the same as for the other
     * test for consistency.
     */

    static Object[] holder;

    @Setup(Level.Iteration)
    public void generateGarbage() {
        holder = new Object[1024*1024*128];
    }

    @Benchmark
    public void gc() {
        System.gc();
    }
}
