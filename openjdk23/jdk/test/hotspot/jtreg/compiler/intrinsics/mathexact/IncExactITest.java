/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8026844
 * @summary Test incrementExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.IncExactITest
 *
 */

package compiler.intrinsics.mathexact;

public class IncExactITest {
    public static int[] values = {1, 1, 1, 1};
    public static void main(String[] args) {
        runTest(new Verify.IncExactI());
    }

    public static void runTest(Verify.UnaryMethod method) {
        for (int i = 0; i < 20000; ++i) {
            Verify.verifyUnary(Integer.MIN_VALUE, method);
            Verify.verifyUnary(Integer.MAX_VALUE - 1, method);
            Verify.verifyUnary(0, method);
            Verify.verifyUnary(values[1], method);
            Verify.verifyUnary(Integer.MAX_VALUE, method);
            Verify.verifyUnary(Integer.MAX_VALUE - values[0] + values[3], method);
            Verify.verifyUnary(Integer.MAX_VALUE - 1 + values[0], method);
        }
    }
}
