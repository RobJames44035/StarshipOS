/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8026844
 * @summary Test negExact loop dependent
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.NegExactILoopDependentTest
 */

package compiler.intrinsics.mathexact;

public class NegExactILoopDependentTest {
    public static void main(String[] args) {
        Verify.LoopDependentTest.verify(new Verify.UnaryToBinary(new Verify.NegExactI()));
    }
}
