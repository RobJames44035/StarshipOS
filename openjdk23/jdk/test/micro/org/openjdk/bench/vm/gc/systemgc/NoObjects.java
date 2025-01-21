/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package org.openjdk.bench.vm.gc.systemgc;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@Fork(value=25, jvmArgs={"-Xmx5g", "-Xms5g", "-Xmn3g", "-XX:+AlwaysPreTouch"})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class NoObjects {

    /*
     * Test the System GC when there are no additionally allocate
     * objects.
     *
     * The heap settings provided are the same as for the other
     * test for consistency.
     */

    @Benchmark
    public void gc() {
        System.gc();
    }

}
