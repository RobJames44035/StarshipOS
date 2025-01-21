/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8026844
 * @summary Test negExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.NegExactILoadTest
 */

package compiler.intrinsics.mathexact;

public class NegExactILoadTest {
    public static void main(String[] args) {
      Verify.LoadTest.init();
      Verify.LoadTest.verify(new Verify.UnaryToBinary(new Verify.NegExactI()));
    }

}
