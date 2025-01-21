/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 * @test
 * @bug 4238511
 * @summary Empty "default/case" statements should not cause the compiler to crash.
 *
 * @run clean EmptyBreak
 * @run compile EmptyBreak.java
 */

// If the test fails to compile, then the bug exists.

public class EmptyBreak {

    public void emptyDefault(int i) {
        switch (i) {
            default:
                // empty!
                break;
        }
    }

    public void emptyCase(int i) {
        switch (i) {
            case 1:
                // empty!
                break;
        }
    }
}
