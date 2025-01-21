/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 8301202
 * @build Tests
 * @build LogTests
 * @run main LogTests
 * @summary Tests for {Math, StrictMath}.log
 */

public class LogTests {
    private LogTests(){}

    public static void main(String... args) {
        int failures = 0;

        failures += testLogSpecialCases();

        if (failures > 0) {
            System.err.println("Testing log incurred "
                               + failures + " failures.");
            throw new RuntimeException();
        }
    }

    private static final double infinityD = Double.POSITIVE_INFINITY;
    private static final double NaNd = Double.NaN;

    /**
     * From the spec for Math.log:
     * "Special cases:
     *
     * If the argument is NaN or less than zero, then the result is NaN.
     * If the argument is positive infinity, then the result is positive infinity.
     * If the argument is positive zero or negative zero, then the result is negative infinity.
     * If the argument is 1.0, then the result is positive zero.
     */
    private static int testLogSpecialCases() {
        int failures = 0;

        double [][] testCases = {
            {Double.NaN,                NaNd},
            {Double.NEGATIVE_INFINITY,  NaNd},
            {-Double.MAX_VALUE,         NaNd},
            {-1.0,                      NaNd},
            {-Double.MIN_NORMAL,        NaNd},
            {-Double.MIN_VALUE,         NaNd},

            {Double.POSITIVE_INFINITY,  infinityD},

            {-0.0,                      -infinityD},
            {+0.0,                      -infinityD},

            {+1.0,                      0.0},
        };

        for(int i = 0; i < testCases.length; i++) {
            failures += testLogCase(testCases[i][0],
                                    testCases[i][1]);
        }

        return failures;
    }

    private static int testLogCase(double input, double expected) {
        int failures=0;

        failures+=Tests.test("Math.log",       input, Math::log,       expected);
        failures+=Tests.test("StrictMath.log", input, StrictMath::log, expected);

        return failures;
    }
}
