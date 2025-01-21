/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8238811
 * @requires vm.debug == true & vm.flavor == "server"
 * @summary Run with -Xcomp to test -XX:+VerifyGraphEdges in debug builds.
 *
 * @run main/othervm -Xbatch -Xcomp -XX:+VerifyGraphEdges compiler.c2.TestVerifyGraphEdges
 */
package compiler.c2;

public class TestVerifyGraphEdges {
    public static void main(String[] args) {
    }
}
