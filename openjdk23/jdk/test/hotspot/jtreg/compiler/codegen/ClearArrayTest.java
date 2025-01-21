/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8260716
 * @summary Test for correct code generation by the JIT
 * @run main/othervm -Xbatch -XX:CompileCommand=compileonly,*ClearArrayTest.test
 *                   -XX:+IgnoreUnrecognizedVMOptions
 *                   -XX:+UnlockDiagnosticVMOptions -XX:-IdealizeClearArrayNode
 *                   compiler.codegen.ClearArrayTest
 */

package compiler.codegen;

public class ClearArrayTest {
    static int[] f1;

    private static void test() {
        f1 = new int[8];
    }

    public static void main(String[] args) {
        for (int i=0; i<15000; i++) {
            test();
        }
    }
}
