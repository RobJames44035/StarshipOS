/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @requires vm.debug == true & vm.compiler2.enabled
 * @run main/othervm -Xbatch -XX:-TieredCompilation
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:+TraceIterativeGVN
 *                   compiler.debug.TraceIterativeGVN
 */

package compiler.debug;

public class TraceIterativeGVN {
    public static void main(String[] args) {
        for (int i = 0; i < 100_000; i++) {
            Byte.valueOf((byte)0);
        }
        System.out.println("TEST PASSED");
    }
}
