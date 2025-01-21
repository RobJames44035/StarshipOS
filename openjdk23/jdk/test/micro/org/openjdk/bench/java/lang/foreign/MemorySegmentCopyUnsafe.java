/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import jdk.internal.misc.Unsafe;

import java.util.concurrent.TimeUnit;

import static java.lang.foreign.ValueLayout.*;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3, jvmArgs = {"--enable-native-access=ALL-UNNAMED", "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED"})
public class MemorySegmentCopyUnsafe {

    static final Unsafe UNSAFE = Utils.unsafe;

    long src;
    long dst;

    @Setup
    public void setup() throws Throwable {
        src = Arena.global().allocate(JAVA_INT).address();
        dst = Arena.global().allocate(JAVA_INT).address();
    }

    @Benchmark
    public void panama() {
        MemorySegment srcSeg = MemorySegment.ofAddress(src).reinterpret(JAVA_INT.byteSize());
        MemorySegment dstSeg = MemorySegment.ofAddress(dst).reinterpret(JAVA_INT.byteSize());
        dstSeg.copyFrom(srcSeg);
    }

    @Benchmark
    public void unsafe() {
        UNSAFE.copyMemory(src, dst, JAVA_INT.byteSize());
    }
}
