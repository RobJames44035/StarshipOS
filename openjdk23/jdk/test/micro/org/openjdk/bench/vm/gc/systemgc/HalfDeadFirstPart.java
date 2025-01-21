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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@Fork(value=25, jvmArgs={"-Xmx5g", "-Xms5g", "-Xmn3g", "-XX:+AlwaysPreTouch"})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class HalfDeadFirstPart {

    /*
     * Test the System GC when half of the objects are dead.
     * In this test the first half of the objects are cleared.
     *
     * The jvmArgs are provided to avoid GCs during object creation.
     */

    static ArrayList<Object[]> holder;

    @Setup(Level.Iteration)
    public void generateGarbage() {
        holder = GarbageGenerator.generateObjectArrays();
        // Clearing every other object array in the holder
        for (int i = 0; i < holder.size() / 2; i++) {
            holder.set(i, null);
        }
    }

    @Benchmark
    public void gc() {
        System.gc();
    }
}
