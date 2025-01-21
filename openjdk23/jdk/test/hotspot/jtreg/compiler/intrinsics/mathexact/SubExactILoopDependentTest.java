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
 * @run main compiler.intrinsics.mathexact.SubExactILoopDependentTest
 */

package compiler.intrinsics.mathexact;

public class SubExactILoopDependentTest {
  public static void main(String[] args) {
      Verify.LoopDependentTest.verify(new Verify.SubExactI());
  }
}
