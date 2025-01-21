/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package org.openjdk.bench.java.math;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 3, time = 5)
@Fork(value = 3)
public class BigIntegerHashCode {

    public enum Group {S, M, L}

    @Param({"S", "M", "L"})
    private Group group;

    private static final int MAX_LENGTH = Arrays.stream(Group.values())
            .mapToInt(p -> getNumbersOfBits(p).length)
            .max()
            .getAsInt();

    private BigInteger[] numbers;

    @Setup
    public void setup() {
        int[] nBits = getNumbersOfBits(group);
        numbers = new BigInteger[MAX_LENGTH];
        for (int i = 0; i < MAX_LENGTH; i++) {
            numbers[i] = Shared.createSingle(nBits[i % nBits.length]);
        }
    }

    private static int[] getNumbersOfBits(Group p) {
        // the below arrays were derived from stats gathered from running tests in
        // the security area, which is the biggest client of BigInteger in JDK
        return switch (p) {
            case S -> new int[]{2, 7, 13, 64};
            case M -> new int[]{256, 384, 511, 512, 521, 767, 768};
            case L -> new int[]{1024, 1025, 2047, 2048, 2049, 3072, 4096, 5120, 6144};
        };
    }

    @Benchmark
    public void testHashCode(Blackhole bh) {
        for (var n : numbers)
            bh.consume(n.hashCode());
    }
}
