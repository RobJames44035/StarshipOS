/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8203444
 * @summary Unit tests for instance versions of String#format
 * @compile Formatted.java
 * @run main Formatted
 */

import java.util.Locale;

public class Formatted {
    public static void main(String[] args) {
        test1();
    }

    /*
     * Test String#formatted(Object... args) functionality.
     */
    static void test1() {
        check("formatted(Object... args)",
                "Test this %s".formatted("and that"),
                String.format("Test this %s", "and that"));
    }

    static void check(String test, String output, String expected) {
        if (output != expected && (output == null || !output.equals(expected))) {
            System.err.println("Testing " + test + ": unexpected result");
            System.err.println("Output: " + output);
            System.err.println("Expected: " + expected);
            throw new RuntimeException();
        }
    }
}
