/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package org.openjdk.bench.java.lang.foreign;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.foreign.ValueLayout.*;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3)
public class SegmentBulkMismatch {

    @Param({"2", "3", "4", "5", "6", "7", "8", "64", "512",
            "4096", "32768", "262144", "2097152", "16777216", "134217728"})
    public int ELEM_SIZE;

    MemorySegment srcNative;
    MemorySegment dstNative;
    byte[] srcArray;
    byte[] dstArray;
    MemorySegment srcHeap;
    MemorySegment dstHeap;

    @Setup
    public void setup() {
        // Always use the same alignment regardless of size
        srcNative = Arena.ofAuto().allocate(ELEM_SIZE,16);
        dstNative = Arena.ofAuto().allocate(ELEM_SIZE, 16);
        var rnd = new Random(42);
        for (int i = 0; i < ELEM_SIZE; i++) {
            srcNative.set(JAVA_BYTE, i, (byte) rnd.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE));
        }
        dstNative.copyFrom(srcNative);
        srcArray = srcNative.toArray(JAVA_BYTE);
        dstArray = dstNative.toArray(JAVA_BYTE);
        srcHeap = MemorySegment.ofArray(srcArray);
        dstHeap = MemorySegment.ofArray(dstArray);
    }

    @Fork(value = 3, jvmArgs = {"-Djava.lang.foreign.native.threshold.power.mismatch=31"})
    @Benchmark
    public long nativeSegmentJava() {
        return srcNative.mismatch(dstNative);
    }

    @Fork(value = 3, jvmArgs = {"-Djava.lang.foreign.native.threshold.power.mismatch=31"})
    @Benchmark
    public long heapSegmentJava() {
        return srcHeap.mismatch(dstHeap);
    }

    @Fork(value = 3, jvmArgs = {"-Djava.lang.foreign.native.threshold.power.mismatch=0"})
    @Benchmark
    public long nativeSegmentUnsafe() {
        return srcNative.mismatch(dstNative);
    }

    @Fork(value = 3, jvmArgs = {"-Djava.lang.foreign.native.threshold.power.mismatch=0"})
    @Benchmark
    public long heapSegmentUnsafe() {
        return srcHeap.mismatch(dstHeap);
    }

    @Benchmark
    public long array() {
        return Arrays.mismatch(srcArray, dstArray);
    }

}

