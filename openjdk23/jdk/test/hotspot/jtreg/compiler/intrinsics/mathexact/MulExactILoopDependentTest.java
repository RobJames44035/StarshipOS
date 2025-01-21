/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8026844
 * @summary Test loop dependent multiplyExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.MulExactILoopDependentTest
 */

package compiler.intrinsics.mathexact;

public class MulExactILoopDependentTest {
    public static void main(String[] args) {
        Verify.LoopDependentTest.verify(new Verify.MulExactI());
    }
}
