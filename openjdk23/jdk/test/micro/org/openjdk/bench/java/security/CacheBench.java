/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package org.openjdk.bench.java.security;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Warmup;
import sun.security.util.Cache;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3, jvmArgs = {"--add-exports", "java.base/sun.security.util=ALL-UNNAMED"})
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class CacheBench {

    @State(Scope.Benchmark)
    public static class SharedState {
        Cache<Integer, Integer> cache;

        @Param({"20480", "204800", "5120000"})
        int size;

        @Param({"86400", "0"})
        int timeout;

        @Setup
        public void setup() {
            cache = Cache.newSoftMemoryCache(size, timeout);
            IntStream.range(0, size).boxed().forEach(i -> cache.put(i, i));
        }
    }

    @State(Scope.Thread)
    public static class GetPutState {
        Integer[] intArray;
        int index;

        @Setup
        public void setup(SharedState benchState) {
            intArray = IntStream.range(0, benchState.size + 1).boxed().toArray(Integer[]::new);
            index = 0;
        }

        @TearDown(Level.Invocation)
        public void tearDown() {
            index++;
            if (index >= intArray.length) {
                index = 0;
            }
        }
    }

    @Benchmark
    public void put(SharedState benchState, GetPutState state) {
        Integer i = state.intArray[state.index];
        benchState.cache.put(i, i);
    }

    @Benchmark
    public Integer get(SharedState benchState, GetPutState state) {
        Integer i = state.intArray[state.index];
        return benchState.cache.get(i);
    }

    @State(Scope.Thread)
    public static class RemoveState {
        Integer[] intArray;
        int index;
        SharedState benchState;

        @Setup
        public void setup(SharedState benchState) {
            this.benchState = benchState;
            intArray = IntStream.range(0, benchState.size).boxed().toArray(Integer[]::new);
            index = 0;
        }

        @TearDown(Level.Invocation)
        public void tearDown() {
            // add back removed item
            Integer i = intArray[index];
            benchState.cache.put(i, i);

            index++;
            if (index >= intArray.length) {
                index = 0;
            }
        }
    }

    @Benchmark
    public void remove(SharedState benchState, RemoveState state) {
        Integer i = state.intArray[state.index];
        benchState.cache.remove(i);
    }
}
