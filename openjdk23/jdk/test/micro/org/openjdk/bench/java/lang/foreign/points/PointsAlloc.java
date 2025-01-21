/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.java.lang.foreign.points;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.bench.java.lang.foreign.points.support.BBPoint;
import org.openjdk.bench.java.lang.foreign.points.support.JNIPoint;
import org.openjdk.bench.java.lang.foreign.points.support.PanamaPoint;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3, jvmArgs = { "--enable-native-access=ALL-UNNAMED", "-Djava.library.path=micro/native" })
public class PointsAlloc {

    @Benchmark
    public Object jni_ByteBuffer_alloc() throws Throwable {
        return new BBPoint(0, 0);
    }

    @Benchmark
    public Object jni_long_alloc() throws Throwable {
        return new JNIPoint(0, 0);
    }

    @Benchmark
    public Object panama_alloc() throws Throwable {
        return new PanamaPoint(0, 0);
    }

}
