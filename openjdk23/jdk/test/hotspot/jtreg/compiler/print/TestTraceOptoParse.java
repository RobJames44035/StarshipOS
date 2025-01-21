/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8293785
 * @summary test for -XX:+TraceOptoParse
 * @requires vm.debug & vm.compiler2.enabled
 * @run main/othervm -XX:+TraceOptoParse compiler.print.TestTraceOptoParse
 *
 */

package compiler.print;

public class TestTraceOptoParse {
    public static void main(String[] args) {
        System.out.println("Passed");
    }
}
