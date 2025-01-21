/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8284981
 * @summary Auto-vectorization enhancement for special counting down loops
 * @requires os.arch=="amd64" | os.arch=="x86_64" | os.arch=="aarch64"
 * @library /test/lib /
 * @run driver compiler.c2.irTests.TestAutoVecCountingDownLoop
 */

public class TestAutoVecCountingDownLoop {
    final private static int ARRLEN = 3000;

    private static int[] a = new int[ARRLEN];
    private static int[] b = new int[ARRLEN];

    public static void main(String[] args) {
        TestFramework.run();
    }


    @Test
    @IR(counts = {IRNode.LOAD_VECTOR_I,  " >0 "})
    @IR(counts = {IRNode.STORE_VECTOR, " >0 "})
    private static void testCountingDown(int[] a, int[] b) {
        for (int i = 2000; i > 0; i--) {
            b[ARRLEN - i] = a[ARRLEN - i];
        }
    }

    @Run(test = "testCountingDown")
    private void testCountingDown_runner() {
        testCountingDown(a, b);
    }
}
