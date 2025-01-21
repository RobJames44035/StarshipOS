/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8026844
 * @summary Test constant multiplyExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.MulExactIConstantTest
 */

package compiler.intrinsics.mathexact;

public class MulExactIConstantTest {
    public static void main(String[] args) {
        Verify.ConstantTest.verify(new Verify.MulExactI());
    }
}
