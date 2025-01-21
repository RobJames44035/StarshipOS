/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
// DivTest.java
// bug-12


/*
 * @test
 *
 * @summary converted from VM Testbase jit/DivTest.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.DivTest.DivTest
 */

package jit.DivTest;

import nsk.share.TestFailure;

public class DivTest{
  static int n;
  static boolean test1 (int n1, int n2) {
    try {
      n = n1 / n2;
      System.out.println(n);
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }
  public static void main(String s[]) {
    boolean failed;
    failed = test1 (-1, 0);
    failed |= !test1 (-1, 0x80000000);
    failed |= !test1 (0, -1);
    failed |= !test1 (0x80000000, -1);
    if (failed)
        throw new TestFailure("Test failed");
  }
}
