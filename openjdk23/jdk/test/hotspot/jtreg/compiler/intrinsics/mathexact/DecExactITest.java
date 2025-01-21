/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8026844
 * @summary Test decrementExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.DecExactITest
 */

package compiler.intrinsics.mathexact;

public class DecExactITest {
    public static int[] values = {1, 1, 1, 1};
    public static int[] minvalues = {Integer.MIN_VALUE, Integer.MIN_VALUE};

    public static void main(String[] args) {
        runTest(new Verify.DecExactI());
    }

    public static void runTest(Verify.UnaryMethod method) {
        for (int i = 0; i < 20000; ++i) {
            Verify.verifyUnary(Integer.MIN_VALUE, method);
            Verify.verifyUnary(minvalues[0], method);
            Verify.verifyUnary(Integer.MIN_VALUE - values[2], method);
            Verify.verifyUnary(0, method);
            Verify.verifyUnary(values[2], method);
            Verify.verifyUnary(Integer.MAX_VALUE, method);
            Verify.verifyUnary(Integer.MIN_VALUE - values[0] + values[3], method);
            Verify.verifyUnary(Integer.MIN_VALUE + 1 - values[0], method);
        }
    }
}
