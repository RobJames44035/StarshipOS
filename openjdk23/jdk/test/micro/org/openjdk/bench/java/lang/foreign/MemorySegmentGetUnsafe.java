/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

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
public class MemorySegmentGetUnsafe {

    static final Unsafe UNSAFE = Utils.unsafe;
    static final MethodHandle OF_ADDRESS_UNSAFE;

    static {
        try {
            OF_ADDRESS_UNSAFE = MethodHandles.lookup().findStatic(MemorySegmentGetUnsafe.class,
                    "ofAddressUnsafe", MethodType.methodType(MemorySegment.class, long.class));
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static final VarHandle INT_HANDLE = adaptSegmentHandle(JAVA_INT.varHandle());

    static VarHandle adaptSegmentHandle(VarHandle handle) {
        handle = MethodHandles.insertCoordinates(handle, 1, 0L);
        handle = MethodHandles.filterCoordinates(handle, 0, OF_ADDRESS_UNSAFE);
        return handle;
    }

    static MemorySegment ofAddressUnsafe(long address) {
        return MemorySegment.ofAddress(address).reinterpret(JAVA_INT.byteSize());
    }

    long addr;

    @Setup
    public void setup() throws Throwable {
        addr = Arena.global().allocate(JAVA_INT).address();
    }

    @Benchmark
    public int panama() {
        return (int) INT_HANDLE.get(addr);
    }

    @Benchmark
    public int unsafe() {
        return UNSAFE.getInt(addr);
    }
}
