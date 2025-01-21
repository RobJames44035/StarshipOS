/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.loopopts.parallel_iv;

import compiler.lib.ir_framework.*;
import jdk.test.lib.Asserts;
import jdk.test.lib.Utils;

import java.util.Random;

/**
 * @test
 * @bug 8328528
 * @summary test the long typed parallel iv replacing transformation for int counted loop
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @run driver compiler.loopopts.parallel_iv.TestParallelIvInIntCountedLoop
 */
public class TestParallelIvInIntCountedLoop {
    private static final Random RNG = Utils.getRandomInstance();

    // stride2 must be a multiple of stride and must not overflow for the optimization to work
    private static final int STRIDE = RNG.nextInt(1, Integer.MAX_VALUE / 16);
    private static final int STRIDE_2 = STRIDE * RNG.nextInt(1, 16);

    public static void main(String[] args) {
        TestFramework.runWithFlags(
                "-XX:+IgnoreUnrecognizedVMOptions", // StressLongCountedLoop is only available in debug builds
                "-XX:StressLongCountedLoop=0", // Don't convert int counted loops to long ones
                "-XX:PerMethodTrapLimit=100" // allow slow-path loop limit checks
        );
    }

    /*
     * The IR framework can only test against static code, and the transformation relies on strides being constants to
     * perform constant propagation. Therefore, we have no choice but repeating the same test case multiple times with
     * different numbers.
     *
     * For good measures, randomly initialized static final stride and stride2 is also tested.
     */

    // A controlled test making sure a simple non-counted loop can be found by the test framework.
    @Test
    @Arguments(values = { Argument.NUMBER_42 }) // otherwise a large number may take too long
    @IR(counts = { IRNode.COUNTED_LOOP, ">=1" })
    private static int testControlledSimpleLoop(int stop) {
        int a = 0;
        for (int i = 0; i < stop; i++) {
            a += i; // cannot be extracted to multiplications
        }

        return a;
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static int testIntCountedLoopWithIntIV(int stop) {
        int a = 0;
        for (int i = 0; i < stop; i++) {
            a += 1;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithIntIV")
    private static void runTestIntCountedLoopWithIntIv() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ(s, testIntCountedLoopWithIntIV(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static int testIntCountedLoopWithIntIVZero(int stop) {
        int a = 0;
        for (int i = 0; i < stop; i++) {
            a += 0;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithIntIVZero")
    private static void runTestIntCountedLoopWithIntIVZero() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ(0, testIntCountedLoopWithIntIVZero(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static int testIntCountedLoopWithIntIVMax(int stop) {
        int a = 0;
        for (int i = 0; i < stop; i++) {
            a += Integer.MAX_VALUE;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithIntIVMax")
    private static void runTestIntCountedLoopWithIntIVMax() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ(s * Integer.MAX_VALUE, testIntCountedLoopWithIntIVMax(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static int testIntCountedLoopWithIntIVMaxMinusOne(int stop) {
        int a = 0;
        for (int i = 0; i < stop; i++) {
            a += Integer.MAX_VALUE - 1;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithIntIVMaxMinusOne")
    private static void runTestIntCountedLoopWithIntIVMaxMinusOne() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ(s * (Integer.MAX_VALUE - 1), testIntCountedLoopWithIntIVMaxMinusOne(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static int testIntCountedLoopWithIntIVMaxPlusOne(int stop) {
        int a = 0;
        for (int i = 0; i < stop; i++) {
            a += Integer.MAX_VALUE + 1;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithIntIVMaxPlusOne")
    private static void runTestIntCountedLoopWithIntIVMaxPlusOne() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ(s * (Integer.MAX_VALUE + 1), testIntCountedLoopWithIntIVMaxPlusOne(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static int testIntCountedLoopWithIntIVWithStrideTwo(int stop) {
        int a = 0;
        for (int i = 0; i < stop; i += 2) {
            a += 2; // this stride2 constant must be a multiple of the first stride (i += ...) for optimization
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithIntIVWithStrideTwo")
    private static void runTestIntCountedLoopWithIntIVWithStrideTwo() {
        // Since we can't easily determine expected values if loop variables overflow when incrementing, we make sure
        // `stop` is less than (MAX_VALUE - stride).
        int s = RNG.nextInt(0, Integer.MAX_VALUE - 2);
        Asserts.assertEQ(Math.ceilDiv(s, 2) * 2, testIntCountedLoopWithIntIVWithStrideTwo(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static int testIntCountedLoopWithIntIVWithStrideMinusOne(int stop) {
        int a = 0;
        for (int i = stop; i > 0; i += -1) {
            a += 1;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithIntIVWithStrideMinusOne")
    private static void runTestIntCountedLoopWithIntIVWithStrideMinusOne() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ(s, testIntCountedLoopWithIntIVWithStrideMinusOne(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static int testIntCountedLoopWithIntIVWithRandomStrides(int stop) {
        int a = 0;
        for (int i = 0; i < stop; i += STRIDE) {
            a += STRIDE_2;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithIntIVWithRandomStrides")
    private static void runTestIntCountedLoopWithIntIVWithRandomStrides() {
        // Make sure `stop` is less than (MAX_VALUE - stride) to avoid overflows.
        int s = RNG.nextInt(0, Integer.MAX_VALUE - STRIDE);
        Asserts.assertEQ(Math.ceilDiv(s, STRIDE) * STRIDE_2, testIntCountedLoopWithIntIVWithRandomStrides(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static int testIntCountedLoopWithIntIVWithRandomStridesAndInits(int init, int init2, int stop) {
        int a = init;
        for (int i = init2; i < stop; i += STRIDE) {
            a += STRIDE_2;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithIntIVWithRandomStridesAndInits")
    private static void runTestIntCountedLoopWithIntIVWithRandomStridesAndInits() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE - STRIDE);
        int init1 = RNG.nextInt();
        int init2 = RNG.nextInt(Integer.MIN_VALUE + s + 1, s); // Limit bounds to avoid loop variables from overflowing.
        Asserts.assertEQ(Math.ceilDiv((s - init2), STRIDE) * STRIDE_2 + init1,
                testIntCountedLoopWithIntIVWithRandomStridesAndInits(init1, init2, s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static long testIntCountedLoopWithLongIV(int stop) {
        long a = 0;
        for (int i = 0; i < stop; i++) {
            a += 1;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithLongIV")
    private static void runTestIntCountedLoopWithLongIV() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ((long) s, testIntCountedLoopWithLongIV(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static long testIntCountedLoopWithLongIVZero(int stop) {
        long a = 0;
        for (int i = 0; i < stop; i++) {
            a += 0;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithLongIVZero")
    private static void runTestIntCountedLoopWithLongIVZero() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ((long) 0, testIntCountedLoopWithLongIVZero(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static long testIntCountedLoopWithLongIVMax(int stop) {
        long a = 0;
        for (int i = 0; i < stop; i++) {
            a += Long.MAX_VALUE;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithLongIVMax")
    private static void runTestIntCountedLoopWithLongIVMax() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ((long) s * Long.MAX_VALUE, testIntCountedLoopWithLongIVMax(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static long testIntCountedLoopWithLongIVMaxMinusOne(int stop) {
        long a = 0;
        for (int i = 0; i < stop; i++) {
            a += Long.MAX_VALUE - 1;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithLongIVMaxMinusOne")
    private static void runTestIntCountedLoopWithLongIVMaxMinusOne() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ((long) s * (Long.MAX_VALUE - 1L), testIntCountedLoopWithLongIVMaxMinusOne(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static long testIntCountedLoopWithLongIVMaxPlusOne(int stop) {
        long a = 0;
        for (int i = 0; i < stop; i++) {
            a += Long.MAX_VALUE + 1;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithLongIVMaxPlusOne")
    private static void runTestIntCountedLoopWithLongIVMaxPlusOne() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ((long) s * (Long.MAX_VALUE + 1L), testIntCountedLoopWithLongIVMaxPlusOne(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static long testIntCountedLoopWithLongIVWithStrideTwo(int stop) {
        long a = 0;
        for (int i = 0; i < stop; i += 2) {
            a += 2;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithLongIVWithStrideTwo")
    private static void runTestIntCountedLoopWithLongIVWithStrideTwo() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE - 2);
        Asserts.assertEQ(Math.ceilDiv(s, 2L) * 2L, testIntCountedLoopWithLongIVWithStrideTwo(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static long testIntCountedLoopWithLongIVWithStrideMinusOne(int stop) {
        long a = 0;
        for (int i = stop; i > 0; i += -1) {
            a += 1;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithLongIVWithStrideMinusOne")
    private static void runTestIntCountedLoopWithLongIVWithStrideMinusOne() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE);
        Asserts.assertEQ((long) s, testIntCountedLoopWithLongIVWithStrideMinusOne(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static long testIntCountedLoopWithLongIVWithRandomStrides(int stop) {
        long a = 0;
        for (int i = 0; i < stop; i += STRIDE) {
            a += STRIDE_2;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithLongIVWithRandomStrides")
    private static void runTestIntCountedLoopWithLongIVWithRandomStrides() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE - STRIDE);
        Asserts.assertEQ(Math.ceilDiv(s, (long) STRIDE) * (long) STRIDE_2,
                testIntCountedLoopWithLongIVWithRandomStrides(s));
    }

    @Test
    @IR(failOn = { IRNode.COUNTED_LOOP })
    private static long testIntCountedLoopWithLongIVWithRandomStridesAndInits(long init, int init2, int stop) {
        long a = init;
        for (int i = init2; i < stop; i += STRIDE) {
            a += STRIDE_2;
        }

        return a;
    }

    @Run(test = "testIntCountedLoopWithLongIVWithRandomStridesAndInits")
    private static void runTestIntCountedLoopWithLongIVWithRandomStridesAndInits() {
        int s = RNG.nextInt(0, Integer.MAX_VALUE - STRIDE);
        long init1 = RNG.nextLong();
        int init2 = RNG.nextInt(Integer.MIN_VALUE + s + 1, s); // Limit bounds to avoid loop variables from overflowing.
        Asserts.assertEQ(Math.ceilDiv(((long) s - init2), (long) STRIDE) * (long) STRIDE_2 + init1,
                testIntCountedLoopWithLongIVWithRandomStridesAndInits(init1, init2, s));
    }
}
