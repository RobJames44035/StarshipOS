/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @build Tests
 * @run main SinCosTests
 * @bug 8302040
 * @summary Tests for {Math, StrictMath}.sqrt
 */

public class SinCosTests {
    private SinCosTests(){}

    public static void main(String... argv) {
        int failures = 0;

        failures += testSin();
        failures += testCos();

        if (failures > 0) {
            System.err.println("Testing sin and cos incurred "
                               + failures + " failures.");
            throw new RuntimeException();
        }
    }

    private static final double InfinityD = Double.POSITIVE_INFINITY;
    private static final double NaNd      = Double.NaN;

    /**
     * "Special cases:
     *
     * If the argument is NaN or an infinity, then the result is NaN.
     *
     * If the argument is zero, then the result is a zero with the
     * same sign as the argument."
     */
    private static int testSin() {
        int failures = 0;

        for(double nan : Tests.NaNs) {
            failures += testSinCase(nan, NaNd);
        }

        double [][] testCases = {
            {+InfinityD,  NaNd},
            {-InfinityD,  NaNd},

            {+0.0,        +0.0},
            {-0.0,        -0.0},

        };

        for(int i = 0; i < testCases.length; i++) {
            failures += testSinCase(testCases[i][0], testCases[i][1]);
        }

        return failures;
    }

    /**
     * "Special cases:
     *
     * If the argument is NaN or an infinity, then the result is NaN.
     * If the argument is zero, then the result is 1.0."
     */
    private static int testCos() {
        int failures = 0;

        for(double nan : Tests.NaNs) {
            failures += testCosCase(nan, NaNd);
        }

        double [][] testCases = {
            {+InfinityD,  NaNd},
            {-InfinityD,  NaNd},

            {+0.0,        +1.0},
            {-0.0,        +1.0},

        };

        for(int i = 0; i < testCases.length; i++) {
            failures += testCosCase(testCases[i][0], testCases[i][1]);
        }

        return failures;
    }

    private static int testSinCase(double input, double expected) {
        int failures=0;

        failures+=Tests.test("Math.sin",        input, Math::sin,        expected);
        failures+=Tests.test("StrictMath.sin",  input, StrictMath::sin,  expected);

        return failures;
    }

    private static int testCosCase(double input, double expected) {
        int failures=0;

        failures+=Tests.test("Math.cos",        input, Math::cos,        expected);
        failures+=Tests.test("StrictMath.cos",  input, StrictMath::cos,  expected);

        return failures;
    }
}
