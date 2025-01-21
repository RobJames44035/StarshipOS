/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8026844
 * @summary Test non constant negExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.NegExactINonConstantTest
 */

package compiler.intrinsics.mathexact;

public class NegExactINonConstantTest {
    public static void main(String[] args) {
        Verify.NonConstantTest.verify(new Verify.UnaryToBinary(new Verify.NegExactI()));
    }
}
