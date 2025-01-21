/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8071961
 * @compile -Xlint:missing-explicit-ctor,-options -Werror --release 8 NoWarningCases.java
 * @compile -Xlint:missing-explicit-ctor          -Werror             NoWarningCases.java
 */

public class NoWarningCases {
    // No explicit constructor; use a default.

    public enum NestedEnum {
        FOO,
        BAR;
        // No explicit constructor; use implicit one.
    }
}
