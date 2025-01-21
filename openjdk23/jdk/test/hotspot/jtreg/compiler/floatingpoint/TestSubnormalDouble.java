/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8295159
 * @summary DSO created with -ffast-math breaks Java floating-point arithmetic
 * @run main/othervm/native -Xlog:os=info compiler.floatingpoint.TestSubnormalDouble
 */

package compiler.floatingpoint;

import static java.lang.System.loadLibrary;

public class TestSubnormalDouble {
    static volatile double lastDouble;

    private static void testDoubles() {
        lastDouble = 0x1.0p-1074;
        for (double x = lastDouble * 2; x <= 0x1.0p1022; x *= 2) {
            if (x != x || x <= lastDouble) {
                throw new RuntimeException("TEST FAILED: " + x);
            }
            lastDouble = x;
        }
    }

    public static void main(String[] args) {
        testDoubles();
        System.out.println("Loading libfast-math.so");
        loadLibrary("fast-math");
        testDoubles();
        System.out.println("Test passed.");
    }
}
