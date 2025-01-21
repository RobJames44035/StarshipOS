/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
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
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import jdk.internal.misc.Unsafe;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 3, jvmArgs = {"--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED" })
public class LoopOverNonConstantFP {

    static final Unsafe unsafe = Utils.unsafe;

    static final int ELEM_SIZE = 1_000_000;
    static final int CARRIER_SIZE = (int)JAVA_DOUBLE.byteSize();
    static final int ALLOC_SIZE = ELEM_SIZE * CARRIER_SIZE;

    Arena arena;
    MemorySegment segmentIn, segmentOut;
    long unsafe_addrIn, unsafe_addrOut;
    ByteBuffer byteBufferIn, byteBufferOut;

    @Setup
    public void setup() {
        unsafe_addrIn = unsafe.allocateMemory(ALLOC_SIZE);
        unsafe_addrOut = unsafe.allocateMemory(ALLOC_SIZE);
        for (int i = 0; i < ELEM_SIZE; i++) {
            unsafe.putDouble(unsafe_addrIn + (i * CARRIER_SIZE), i);
        }
        for (int i = 0; i < ELEM_SIZE; i++) {
            unsafe.putDouble(unsafe_addrOut + (i * CARRIER_SIZE), i);
        }
        arena = Arena.ofConfined();
        segmentIn = arena.allocate(ALLOC_SIZE, 1);
        segmentOut = arena.allocate(ALLOC_SIZE, 1);
        for (int i = 0; i < ELEM_SIZE; i++) {
            segmentIn.setAtIndex(JAVA_DOUBLE, i, i);
        }
        for (int i = 0; i < ELEM_SIZE; i++) {
            segmentOut.setAtIndex(JAVA_DOUBLE, i, i);
        }
        byteBufferIn = ByteBuffer.allocateDirect(ALLOC_SIZE).order(ByteOrder.nativeOrder());
        byteBufferOut = ByteBuffer.allocateDirect(ALLOC_SIZE).order(ByteOrder.nativeOrder());
        for (int i = 0; i < ELEM_SIZE; i++) {
            byteBufferIn.putDouble(i * CARRIER_SIZE , i);
        }
        for (int i = 0; i < ELEM_SIZE; i++) {
            byteBufferOut.putDouble(i * CARRIER_SIZE , i);
        }
    }

    @TearDown
    public void tearDown() {
        arena.close();
        unsafe.invokeCleaner(byteBufferIn);
        unsafe.invokeCleaner(byteBufferOut);
        unsafe.freeMemory(unsafe_addrIn);
        unsafe.freeMemory(unsafe_addrOut);
    }

    @Benchmark
    public void unsafe_loop() {
        for (int i = 0; i < ELEM_SIZE; i ++) {
            unsafe.putDouble(unsafe_addrOut + (i * CARRIER_SIZE),
                    unsafe.getDouble(unsafe_addrIn + (i * CARRIER_SIZE)) +
                    unsafe.getDouble(unsafe_addrOut + (i * CARRIER_SIZE)));
        }
    }

    @Benchmark
    public void segment_loop() {
        for (int i = 0; i < ELEM_SIZE; i ++) {
            segmentOut.setAtIndex(JAVA_DOUBLE, i,
                    segmentIn.getAtIndex(JAVA_DOUBLE, i) +
                    segmentOut.getAtIndex(JAVA_DOUBLE, i));
        }
    }

    @Benchmark
    public void BB_loop() {
        for (int i = 0; i < ELEM_SIZE; i++) {
            byteBufferOut.putDouble(i * CARRIER_SIZE,
                    byteBufferIn.getDouble(i * CARRIER_SIZE) +
                    byteBufferOut.getDouble(i * CARRIER_SIZE));
        }
    }
}
