/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.java.lang.foreign.points;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.bench.java.lang.foreign.points.support.JNIPoint;
import org.openjdk.bench.java.lang.foreign.points.support.PanamaPoint;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3, jvmArgs = { "--enable-native-access=ALL-UNNAMED", "-Djava.library.path=micro/native" })
public class PointsFree {

    JNIPoint jniPoint;
    PanamaPoint panamaPoint;

    @Setup(Level.Invocation)
    public void setup() {
        jniPoint = new JNIPoint(0, 0);
        panamaPoint = new PanamaPoint(0, 0);
    }

    @Benchmark
    public void jni_long_free() throws Throwable {
        jniPoint.close();
    }

    @Benchmark
    public void panama_free() throws Throwable {
        panamaPoint.close();
    }

}
