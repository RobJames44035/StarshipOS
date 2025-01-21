/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8026844
 * @summary Test constant mulExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.MulExactLConstantTest
 */

package compiler.intrinsics.mathexact;

public class MulExactLConstantTest {
    public static void main(String[] args) {
        Verify.ConstantLongTest.verify(new Verify.MulExactL());
    }
}
