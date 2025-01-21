/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @run main BigIntegerParallelMultiplyTest
 * @summary tests parallelMultiply() method in BigInteger
 * @author Heinz Kabutz heinz@javaspecialists.eu
 */

import java.math.BigInteger;
import java.util.function.BinaryOperator;

/**
 * This is a simple test class created to ensure that the results
 * of multiply() are the same as multiplyParallel(). We calculate
 * the Fibonacci numbers using Dijkstra's sum of squares to get
 * very large numbers (hundreds of thousands of bits).
 *
 * @author Heinz Kabutz, heinz@javaspecialists.eu
 */
public class BigIntegerParallelMultiplyTest {
    public static BigInteger fibonacci(int n, BinaryOperator<BigInteger> multiplyOperator) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;

        int half = (n + 1) / 2;
        BigInteger f0 = fibonacci(half - 1, multiplyOperator);
        BigInteger f1 = fibonacci(half, multiplyOperator);
        if (n % 2 == 1) {
            BigInteger b0 = multiplyOperator.apply(f0, f0);
            BigInteger b1 = multiplyOperator.apply(f1, f1);
            return b0.add(b1);
        } else {
            BigInteger b0 = f0.shiftLeft(1).add(f1);
            return multiplyOperator.apply(b0, f1);
        }
    }

    public static void main(String[] args) throws Exception {
        compare(1000, 324);
        compare(10_000, 3473);
        compare(100_000, 34883);
        compare(1_000_000, 347084);
    }

    private static void compare(int n, int expectedBitCount) {
        BigInteger multiplyResult = fibonacci(n, BigInteger::multiply);
        BigInteger parallelMultiplyResult = fibonacci(n, BigInteger::parallelMultiply);
        checkBitCount(n, expectedBitCount, multiplyResult);
        checkBitCount(n, expectedBitCount, parallelMultiplyResult);
        if (!multiplyResult.equals(parallelMultiplyResult))
            throw new AssertionError("multiply() and parallelMultiply() give different results");
    }

    private static void checkBitCount(int n, int expectedBitCount, BigInteger number) {
        if (number.bitCount() != expectedBitCount)
            throw new AssertionError(
                    "bitCount of fibonacci(" + n + ") was expected to be " + expectedBitCount
                            + " but was " + number.bitCount());
    }
}
