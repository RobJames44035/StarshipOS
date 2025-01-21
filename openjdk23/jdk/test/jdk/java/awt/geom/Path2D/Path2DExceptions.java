/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.awt.geom.Path2D;

import static java.awt.geom.Path2D.WIND_EVEN_ODD;
import static java.awt.geom.Path2D.WIND_NON_ZERO;

/*
 * @test
 * @bug 6606673
 * @summary The test checks if correct exceptions are thrown by the constructors
 */
public final class Path2DExceptions {

    public static void main(String[] args) {
        testFloat();
        testDouble();
    }

    private static void testFloat() {
        try {
            new Path2D.Float(null);
            throw new RuntimeException("NullPointerException is expected");
        } catch (NullPointerException ignore) {
            // expected
        }
        try {
            new Path2D.Float(null, null);
            throw new RuntimeException("NullPointerException is expected");
        } catch (NullPointerException ignore) {
            // expected
        }

        try {
            new Path2D.Float(-1);
            throw new RuntimeException("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ignore) {
            // expected
        }
        try {
            new Path2D.Float(-1, 0);
            throw new RuntimeException("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ignore) {
            // expected
        }

        try {
            new Path2D.Float(WIND_EVEN_ODD, -1);
            throw new RuntimeException("NegativeArraySizeException is expected");
        } catch (NegativeArraySizeException ignore) {
            // expected
        }
        try {
            new Path2D.Float(WIND_NON_ZERO, -1);
            throw new RuntimeException("NegativeArraySizeException is expected");
        } catch (NegativeArraySizeException ignore) {
            // expected
        }
    }

    private static void testDouble() {
        try {
            new Path2D.Double(null);
            throw new RuntimeException("NullPointerException is expected");
        } catch (NullPointerException ignore) {
            // expected
        }
        try {
            new Path2D.Double(null, null);
            throw new RuntimeException("NullPointerException is expected");
        } catch (NullPointerException ignore) {
            // expected
        }

        try {
            new Path2D.Double(-1);
            throw new RuntimeException("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ignore) {
            // expected
        }
        try {
            new Path2D.Double(-1, 0);
            throw new RuntimeException("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ignore) {
            // expected
        }

        try {
            new Path2D.Double(WIND_EVEN_ODD, -1);
            throw new RuntimeException("NegativeArraySizeException is expected");
        } catch (NegativeArraySizeException ignore) {
            // expected
        }
        try {
            new Path2D.Double(WIND_NON_ZERO, -1);
            throw new RuntimeException("NegativeArraySizeException is expected");
        } catch (NegativeArraySizeException ignore) {
            // expected
        }
    }
}
