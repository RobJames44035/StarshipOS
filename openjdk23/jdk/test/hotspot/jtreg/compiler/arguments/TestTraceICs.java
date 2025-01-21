/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8217447
 * @summary Test running with log:inlinecache=trace enabled.
 * @run main/othervm -Xlog:inlinecache=trace
 *                   compiler.arguments.TestTraceICs
 */

package compiler.arguments;

public class TestTraceICs {

    static public void main(String[] args) {
        System.out.println("Passed");
    }
}

