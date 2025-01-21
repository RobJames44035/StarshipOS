/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.java.lang.foreign.points;

import org.openjdk.bench.java.lang.foreign.points.support.BBPoint;
import org.openjdk.bench.java.lang.foreign.points.support.JNIPoint;
import org.openjdk.bench.java.lang.foreign.points.support.PanamaPoint;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3, jvmArgs = { "--enable-native-access=ALL-UNNAMED", "-Djava.library.path=micro/native" })
public class PointsDistance {

    BBPoint jniP1;
    BBPoint jniP2;

    JNIPoint nativeP1;
    JNIPoint nativeP2;

    PanamaPoint panamaPointP1;
    PanamaPoint panamaPointP2;

    @Setup
    public void setup() {
        jniP1 = new BBPoint(0, 0);
        jniP2 = new BBPoint(1, 1);

        nativeP1 = new JNIPoint(0, 0);
        nativeP2 = new JNIPoint(1, 1);

        panamaPointP1 = new PanamaPoint(0, 0);
        panamaPointP2 = new PanamaPoint(1, 1);
    }

    @TearDown
    public void tearDown() {
        nativeP1.free();
        nativeP2.free();

        panamaPointP1.close();
        panamaPointP2.close();
    }

    @Benchmark
    public double jni_ByteBuffer() throws Throwable {
        return jniP1.distanceTo(jniP2);
    }

    @Benchmark
    public double jni_long() throws Throwable {
        return nativeP1.distanceTo(nativeP2);
    }

    @Benchmark
    public double panama_MemorySegment() throws Throwable {
        return panamaPointP1.distanceTo(panamaPointP2);
    }

    @Benchmark
    public double panama_MemoryAddress() throws Throwable {
        return panamaPointP1.distanceToPtrs(panamaPointP2);
    }

}
