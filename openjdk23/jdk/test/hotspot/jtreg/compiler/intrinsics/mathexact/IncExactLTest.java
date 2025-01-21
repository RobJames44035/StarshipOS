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
 * @run main compiler.intrinsics.mathexact.IncExactLTest
 */

package compiler.intrinsics.mathexact;

public class IncExactLTest {
    public static long[] values = {1, 1, 1, 1};
    public static void main(String[] args) {
        runTest(new Verify.IncExactL());
    }

    public static void runTest(Verify.UnaryLongMethod method) {
        for (int i = 0; i < 20000; ++i) {
            Verify.verifyUnary(Long.MIN_VALUE, method);
            Verify.verifyUnary(Long.MAX_VALUE - 1, method);
            Verify.verifyUnary(0, method);
            Verify.verifyUnary(values[1], method);
            Verify.verifyUnary(Long.MAX_VALUE, method);
            Verify.verifyUnary(Long.MAX_VALUE - values[0] + values[3], method);
            Verify.verifyUnary(Long.MAX_VALUE - 1 + values[0], method);
        }
    }
}
