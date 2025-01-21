/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.jdk.preview.patterns;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Tests Exactness methods
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations=5, time=1)
@Measurement(iterations=5, time=1)
@Threads(2)
@Fork(value = 1,
      jvmArgs = {"-Djmh.blackhole.mode=COMPILER",
                        "--enable-preview"})
@State(Scope.Thread)
@SuppressWarnings("preview")
public class Exactness {

    private static boolean int_float_based_on_leading_trailing(int n) {
        if (n == Integer.MIN_VALUE)
            return true;
        n = Math.abs(n);
        return Float.PRECISION >= // 24
                (32 - (Integer.numberOfLeadingZeros(n) +
                        Integer.numberOfTrailingZeros(n))) ;
    }
    @Benchmark
    public void test_int_float_based_on_leading_trailing(Blackhole bh) {
        for(int n = Integer.MIN_VALUE; n < Integer.MAX_VALUE; n++) {
            bh.consume(int_float_based_on_leading_trailing(n));
        }
    }

    private static boolean int_float_based_on_filtering(int n) {
        return n == (int)(float)n && n != Integer.MAX_VALUE;
    }
    @Benchmark
    public void test_int_float_based_on_filtering(Blackhole bh) {
        for(int n = Integer.MIN_VALUE; n < Integer.MAX_VALUE; n++) {
            bh.consume(int_float_based_on_filtering(n));
        }
    }

    private static boolean long_float_based_on_leading_trailing(long n) {
        if (n == Long.MIN_VALUE)
            return true;
        n = Math.abs(n);
        return Float.PRECISION >= // 24
                (64 - (Long.numberOfLeadingZeros(n) +
                        Long.numberOfTrailingZeros(n))) ;
    }
    @Benchmark
    public void test_long_float_based_on_leading_trailing(Blackhole bh) {
        for(int n = Integer.MIN_VALUE; n < Integer.MAX_VALUE; n++) {
            bh.consume(long_float_based_on_leading_trailing(n));
        }
    }

    private static boolean long_float_based_on_filtering(long n) {
        return n == (long)(float)n && n != Long.MAX_VALUE;
    }
    @Benchmark
    public void test_long_float_based_on_filtering(Blackhole bh) {
        for(int n = Integer.MIN_VALUE; n < Integer.MAX_VALUE; n++) {
            bh.consume(long_float_based_on_filtering(n));
        }
    }

    private static boolean long_double_based_on_leading_trailing(long n) {
        if (n == Long.MIN_VALUE)
            return true;
        n = Math.abs(n);
        return Double.PRECISION >= // 53
                (64 - (Long.numberOfLeadingZeros(n) +
                        Long.numberOfTrailingZeros(n))) ;
    }
    @Benchmark
    public void test_long_double_based_on_leading_trailing(Blackhole bh) {
        for(int n = Integer.MIN_VALUE; n < Integer.MAX_VALUE; n++) {
            bh.consume(long_double_based_on_leading_trailing(n));
        }
    }

    private static boolean long_double_based_on_filtering(long n) {
        return n == (long)(double)n && n != Long.MAX_VALUE;
    }
    @Benchmark
    public void test_long_double_based_on_filtering(Blackhole bh) {
        for(int n = Integer.MIN_VALUE; n < Integer.MAX_VALUE; n++) {
            bh.consume(long_double_based_on_filtering(n));
        }
    }

    private static boolean float_int_based_on_compare(float n) {
        return Double.compare((double)n, (double)((int)n)) == 0;
    }
    @Benchmark
    public void test_float_int_based_on_compare(Blackhole bh) {
        float n = -Float.MAX_VALUE;
        while (n <= Float.MAX_VALUE) {
            bh.consume(float_int_based_on_compare(n));
            n = Math.nextUp(n);
        }
    }

    private static boolean isNegativeZero(float n) {
        return Float.floatToRawIntBits(n) == Integer.MIN_VALUE;
    }
    private static boolean float_int_based_on_filtering(float n) {
        return n == (float)(int)n && n != 0x1p31f && !isNegativeZero(n);
    }
    @Benchmark
    public void test_float_int_based_on_filtering(Blackhole bh) {
        float n = -Float.MAX_VALUE;
        while (n <= Float.MAX_VALUE) {
            bh.consume(float_int_based_on_filtering(n));
            n = Math.nextUp(n);
        }
    }
}
