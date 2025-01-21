/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.c2;

/*
 * @test
 * @bug 8279076
 * @summary SqrtD/SqrtF should be matched only on supported platforms
 * @requires vm.debug
 *
 * @run main/othervm -XX:-TieredCompilation -Xcomp
 *                   -XX:CompileOnly=compiler.c2.TestSqrt::*
 *                   -XX:CompileOnly=java.lang.Math::*
 *                   compiler.c2.TestSqrt
 */
public class TestSqrt {
    static float srcF = 42.0f;
    static double srcD = 42.0d;
    static float dstF;
    static double dstD;

    public static void test() {
        dstF = (float)Math.sqrt((double)srcF);
        dstD = Math.sqrt(srcD);
    }

    public static void main(String args[]) {
        for (int i = 0; i < 20_000; i++) {
            test();
        }
    }
}
