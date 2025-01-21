/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 6897150
 * @summary Hotspot optimises away a valid loop
 *
 * @run main compiler.c2.Test6897150
 */

package compiler.c2;

// Should be compiled with javac from JDK1.3 to get bytecode which shows the problem.
public class Test6897150 {
    public static void main(String[] args) {
        // This works
        loopAndPrint(Integer.MAX_VALUE -1);
        // This doesn't
        loopAndPrint(Integer.MAX_VALUE);
    }

    static void verify(int max, int a) {
        if ( a != (max - 1)) {
            System.out.println("Expected: " + (max - 1));
            System.out.println("Actual  : " + a);
            System.exit(97);
        }
    }
    static void loopAndPrint(int max) {
        int a = -1;
        int i = 1;
        for (; i < max; i++) {
            a = i;
        }
        verify(max, a);
    }
}

