/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8026844
 * @summary Test constant negExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.NegExactLConstantTest
 */

package compiler.intrinsics.mathexact;

public class NegExactLConstantTest {
    public static void main(String[] args) {
        Verify.ConstantLongTest.verify(new Verify.UnaryToBinaryLong(new Verify.NegExactL()));
    }
}
