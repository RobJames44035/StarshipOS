/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8245801
 * @requires vm.debug
 * @summary Test running with StressRecompilation enabled.
 * @run main/othervm -Xcomp -XX:+IgnoreUnrecognizedVMOptions -XX:+StressRecompilation
 *                   compiler.c2.TestStressRecompilation
 */

package compiler.c2;

public class TestStressRecompilation {

    public static void main(String[] args) {
        System.out.println("Test passed.");
    }
}
