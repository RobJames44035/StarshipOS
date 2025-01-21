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

import static java.lang.foreign.ValueLayout.JAVA_INT;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 3, jvmArgs = { "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED" })
public class LoopOverNonConstantShared extends JavaLayouts {

    static final Unsafe unsafe = Utils.unsafe;

    static final int ELEM_SIZE = 1_000_000;
    static final int CARRIER_SIZE = (int)JAVA_INT.byteSize();
    static final int ALLOC_SIZE = ELEM_SIZE * CARRIER_SIZE;
    Arena arena;
    MemorySegment segment;
    long unsafe_addr;

    ByteBuffer byteBuffer;

    @Setup
    public void setup() {
        unsafe_addr = unsafe.allocateMemory(ALLOC_SIZE);
        for (int i = 0; i < ELEM_SIZE; i++) {
            unsafe.putInt(unsafe_addr + (i * CARRIER_SIZE) , i);
        }
        arena = Arena.ofConfined();
        segment = arena.allocate(ALLOC_SIZE, CARRIER_SIZE);
        for (int i = 0; i < ELEM_SIZE; i++) {
            VH_INT.set(segment, (long) i, i);
        }
        byteBuffer = ByteBuffer.allocateDirect(ALLOC_SIZE).order(ByteOrder.nativeOrder());
        for (int i = 0; i < ELEM_SIZE; i++) {
            byteBuffer.putInt(i * CARRIER_SIZE , i);
        }
    }

    @TearDown
    public void tearDown() {
        arena.close();
        unsafe.invokeCleaner(byteBuffer);
        unsafe.freeMemory(unsafe_addr);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int unsafe_get() {
        return unsafe.getInt(unsafe_addr);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int segment_get() {
        return (int) VH_INT.get(segment, 0L);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int BB_get() {
        return byteBuffer.getInt(0);
    }

    @Benchmark
    public int unsafe_loop() {
        int res = 0;
        for (int i = 0; i < ELEM_SIZE; i ++) {
            res += unsafe.getInt(unsafe_addr + (i * CARRIER_SIZE));
        }
        return res;
    }

    @Benchmark
    public int segment_loop_instance() {
        int res = 0;
        for (int i = 0; i < ELEM_SIZE; i ++) {
            res += segment.get(JAVA_INT, i * CARRIER_SIZE);
        }
        return res;
    }

    @Benchmark
    public int segment_loop_instance_address() {
        int res = 0;
        for (int i = 0; i < ELEM_SIZE; i ++) {
            res += segment.get(JAVA_INT, i * CARRIER_SIZE);
        }
        return res;
    }

    @Benchmark
    public int segment_loop() {
        int sum = 0;
        for (int i = 0; i < ELEM_SIZE; i++) {
            sum += (int) VH_INT.get(segment, (long) i);
        }
        return sum;
    }

    @Benchmark
    public int segment_loop_slice() {
        int sum = 0;
        MemorySegment base = segment.asSlice(0, segment.byteSize());
        for (int i = 0; i < ELEM_SIZE; i++) {
            sum += (int) VH_INT.get(base, (long) i);
        }
        return sum;
    }

    @Benchmark
    public int segment_loop_readonly() {
        int sum = 0;
        MemorySegment base = segment.asReadOnly();
        for (int i = 0; i < ELEM_SIZE; i++) {
            sum += (int) VH_INT.get(base, (long) i);
        }
        return sum;
    }

    @Benchmark
    public int BB_loop() {
        int sum = 0;
        ByteBuffer bb = byteBuffer;
        for (int i = 0; i < ELEM_SIZE; i++) {
            sum += bb.getInt(i * CARRIER_SIZE);
        }
        return sum;
    }

}
