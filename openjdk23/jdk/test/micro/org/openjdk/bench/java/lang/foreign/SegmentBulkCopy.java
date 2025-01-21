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
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3)
public class SegmentBulkCopy {

    @Param({"2", "3", "4", "5", "6", "7", "8", "64", "512",
            "4096", "32768", "262144", "2097152", "16777216", "134217728"})
    public int ELEM_SIZE;

    byte[] srcArray;
    byte[] dstArray;
    MemorySegment heapSrcSegment;
    MemorySegment heapDstSegment;
    MemorySegment nativeSrcSegment;
    MemorySegment nativeDstSegment;
    ByteBuffer srcBuffer;
    ByteBuffer dstBuffer;

    @Setup
    public void setup() {
        srcArray = new byte[ELEM_SIZE];
        dstArray = new byte[ELEM_SIZE];
        heapSrcSegment = MemorySegment.ofArray(srcArray);
        heapDstSegment = MemorySegment.ofArray(dstArray);
        nativeSrcSegment = Arena.ofAuto().allocate(ELEM_SIZE);
        nativeDstSegment = Arena.ofAuto().allocate(ELEM_SIZE);
        srcBuffer = ByteBuffer.wrap(srcArray);
        dstBuffer = ByteBuffer.wrap(dstArray);
    }

    @Benchmark
    public void arrayCopy() {
        System.arraycopy(srcArray, 0, dstArray, 0, ELEM_SIZE);
    }

    @Benchmark
    public void bufferCopy() {
        dstBuffer.put(srcBuffer);
    }

    @Fork(value = 3, jvmArgs = {"-Djava.lang.foreign.native.threshold.power.copy=31"})
    @Benchmark
    public void heapSegmentCopyJava() {
        MemorySegment.copy(heapSrcSegment, 0, heapDstSegment, 0, ELEM_SIZE);
    }

    @Fork(value = 3, jvmArgs = {"-Djava.lang.foreign.native.threshold.power.copy=0"})
    @Benchmark
    public void heapSegmentCopyUnsafe() {
        MemorySegment.copy(heapSrcSegment, 0, heapDstSegment, 0, ELEM_SIZE);
    }

    @Fork(value = 3, jvmArgs = {"-Djava.lang.foreign.native.threshold.power.copy=31"})
    @Benchmark
    public void nativeSegmentCopyJava() {
        MemorySegment.copy(nativeSrcSegment, 0, nativeDstSegment, 0, ELEM_SIZE);
    }

    @Fork(value = 3, jvmArgs = {"-Djava.lang.foreign.native.threshold.power.copy=0"})
    @Benchmark
    public void nativeSegmentCopyUnsafe() {
        MemorySegment.copy(nativeSrcSegment, 0, nativeDstSegment, 0, ELEM_SIZE);
    }

}
