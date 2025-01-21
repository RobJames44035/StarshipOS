/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8026844
 * @summary Test non constant multiplyExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.MulExactINonConstantTest
 */

package compiler.intrinsics.mathexact;

public class MulExactINonConstantTest {
    public static void main(String[] args) {
        Verify.NonConstantTest.verify(new Verify.MulExactI());
        Verify.LoadTest.verify(new Verify.MulExactI());
    }
}
