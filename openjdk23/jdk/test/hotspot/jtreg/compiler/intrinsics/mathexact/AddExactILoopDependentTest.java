/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8024924
 * @summary Test non constant addExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.AddExactILoopDependentTest
 */

package compiler.intrinsics.mathexact;

public class AddExactILoopDependentTest {
  public static void main(String[] args) {
    Verify.LoopDependentTest.verify(new Verify.AddExactI());
  }
}
