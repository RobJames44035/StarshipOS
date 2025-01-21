/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @run main MultiplicationTests
 * @bug 5100935 8188044
 * @summary Tests for multiplication methods (use -Dseed=X to set PRNG seed)
 * @key randomness
 */

import java.math.BigInteger;
import java.util.function.BiFunction;
import jdk.test.lib.RandomFactory;

public class MultiplicationTests {
    private MultiplicationTests(){}

    // Number of random products to test.
    private static final int COUNT = 1 << 16;

    // Initialize shared random number generator
    private static java.util.Random rnd = RandomFactory.getRandom();

    // Calculate high 64 bits of 128 product using BigInteger.
    private static long multiplyHighBigInt(long x, long y) {
        return BigInteger.valueOf(x).multiply(BigInteger.valueOf(y))
            .shiftRight(64).longValue();
    }

    // Calculate high 64 bits of unsigned 128 product using signed multiply
    private static long unsignedMultiplyHigh(long x, long y) {
        long x0 = x & 0xffffffffL;
        long x1 = x >>> 32;
        long y0 = y & 0xffffffffL;
        long y1 = y >>> 32;

        long t = x1 * y0 + ((x0 * y0) >>> 32);
        long z0 = x0 * y1 + (t & 0xffffffffL);
        long z1 = t >>> 32;

        return x1 * y1 + z1 + (z0 >>> 32);
    }

    // Compare results of two functions for a pair of values
    private static boolean check(BiFunction<Long,Long,Long> reference,
        BiFunction<Long,Long,Long> multiply, long x, long y) {
        long p1 = reference.apply(x, y);
        long p2 = multiply.apply(x, y);
        if (p1 != p2) {
            System.err.printf("Error - x:%d y:%d p1:%d p2:%d\n", x, y, p1, p2);
            return false;
        } else {
            return true;
        }
    }

    // Check Math.multiplyHigh(x,y) against multiplyHighBigInt(x,y)
    private static boolean checkSigned(long x, long y) {
        return check((a,b) -> multiplyHighBigInt(a,b),
            (a,b) -> Math.multiplyHigh(a, b), x, y);
    }

    // Check Math.unsignedMultiplyHigh(x,y) against unsignedMultiplyHigh(x,y)
    private static boolean checkUnsigned(long x, long y) {
        return check((a,b) -> unsignedMultiplyHigh(a,b),
            (a,b) -> Math.unsignedMultiplyHigh(a, b), x, y);
    }

    private static int test(BiFunction<Long,Long,Boolean> chk) {
        int failures = 0;

        // check some boundary cases
        long[][] v = new long[][]{
            {0L, 0L},
            {-1L, 0L},
            {0L, -1L},
            {1L, 0L},
            {0L, 1L},
            {-1L, -1L},
            {-1L, 1L},
            {1L, -1L},
            {1L, 1L},
            {Long.MAX_VALUE, Long.MAX_VALUE},
            {Long.MAX_VALUE, -Long.MAX_VALUE},
            {-Long.MAX_VALUE, Long.MAX_VALUE},
            {Long.MAX_VALUE, Long.MIN_VALUE},
            {Long.MIN_VALUE, Long.MAX_VALUE},
            {Long.MIN_VALUE, Long.MIN_VALUE}
        };

        for (long[] xy : v) {
            if(!chk.apply(xy[0], xy[1])) {
                failures++;
            }
        }

        // check some random values
        for (int i = 0; i < COUNT; i++) {
            if (!chk.apply(rnd.nextLong(), rnd.nextLong())) {
                failures++;
            }
        }

        return failures;
    }

    private static int testMultiplyHigh() {
        return test((x,y) -> checkSigned(x,y));
    }

    private static int testUnsignedMultiplyHigh() {
        return test((x,y) -> checkUnsigned(x,y));
    }

    public static void main(String argv[]) {
        int failures = testMultiplyHigh() + testUnsignedMultiplyHigh();

        if (failures > 0) {
            System.err.println("Multiplication testing encountered "
                               + failures + " failures.");
            throw new RuntimeException();
        } else {
            System.out.println("MultiplicationTests succeeded");
        }
    }
}
