/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package org.openjdk.bench.java.util;

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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class HashMapBench {
    private Supplier<Map<Integer, Integer>> mapSupplier;
    private Map<Integer, Integer> bigMapToAdd;

    @Param("1000000")
    private int size;

    @Param
    private MapType mapType;

    public enum MapType {
        HASH_MAP,
        LINKED_HASH_MAP,
    }

    @Setup
    public void setup() {
        switch (mapType) {
        case HASH_MAP:
            mapSupplier = () -> new HashMap<>();
            break;
        case LINKED_HASH_MAP:
            mapSupplier = () -> new LinkedHashMap<>();
            break;
        default:
            throw new AssertionError();
        }

        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        this.bigMapToAdd = IntStream.range(0, size).boxed()
            .collect(toMap(i -> 7 + i * 128, i -> rnd.nextInt()));
    }

    @Benchmark
    public int putAllWithBigMapToNonEmptyMap() {
        Map<Integer, Integer> map = mapSupplier.get();
        map.put(-1, -1);
        map.putAll(bigMapToAdd);
        return map.size();
    }

    @Benchmark
    public int putAllWithBigMapToEmptyMap() {
        Map<Integer, Integer> map = mapSupplier.get();
        map.putAll(bigMapToAdd);
        return map.size();
    }

    @Benchmark
    public int put() {
        Map<Integer, Integer> map = mapSupplier.get();
        for (int k : bigMapToAdd.keySet()) {
            map.put(k, bigMapToAdd.get(k));
        }
        return map.size();
    }
}
