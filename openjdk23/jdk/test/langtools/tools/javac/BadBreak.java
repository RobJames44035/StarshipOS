/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 * @test
 * @bug 4035335
 * @summary Incorrect dead code analysis when break statements break
 * themselves.
 *
 * @run clean BadBreak
 * @run compile BadBreak.java
 * @run main BadBreak
 */

// The test fails if the code fails to compile.

public class BadBreak {
    public static void main(String [] args) {
    label:
        break label;
        System.out.println("I am correctly reached!");
    }
}
