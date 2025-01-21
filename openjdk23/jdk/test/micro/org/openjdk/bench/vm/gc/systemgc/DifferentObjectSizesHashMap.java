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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@Fork(value=25, jvmArgs={"-Xmx5g", "-Xms5g", "-Xmn3g", "-XX:+AlwaysPreTouch"})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class DifferentObjectSizesHashMap {

    /*
     * Test the System GC when 2/3 of the objects are live
     * and kept reachable through a HashMap.
     *
     * The jvmArgs are provided to avoid GCs during object creation.
     */

    static HashMap<Integer, byte[]> largeMap;

    @Setup(Level.Iteration)
    public void generateGarbage() {
        largeMap = GarbageGenerator.generateAndFillHashMap(false);
        int numberOfObjects = largeMap.size();
        // Removing a third of the objects and keeping a good
        // distribution of sizes.
        for (int i = 0; i < numberOfObjects; i++) {
            if (i%3 == 0) {
                largeMap.remove(i);
            }
        }
    }

    @Benchmark
    public void gc() {
        System.gc();
    }
}
