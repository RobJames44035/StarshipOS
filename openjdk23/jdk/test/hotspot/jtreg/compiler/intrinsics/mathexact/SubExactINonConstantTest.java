/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8026844
 * @summary Test non constant subtractExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.SubExactINonConstantTest
 */

package compiler.intrinsics.mathexact;

public class SubExactINonConstantTest {
    public static void main(String[] args) {
        Verify.NonConstantTest.verify(new Verify.SubExactI());
    }
}
