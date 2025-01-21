/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
// tests exception handler inside optimizable loops and around them


/*
 * @test
 *
 * @summary converted from VM Testbase jit/ExcOpt.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.ExcOpt.ExcOpt
 */

package jit.ExcOpt;

import nsk.share.TestFailure;

public class ExcOpt {
  static int x;

  public static void main (String s[]) {

    x = 0;

    try {
      for (int i = 1; i < 100; i++) {
        x += 1;
      }
    } catch (Exception e) {
      x = 0;
    }

    for (int i=1; i < 100; i++) {
      try {
        x += 1;
      } catch (Exception e) {
        x = 0;
      }
    }

    System.out.println("Done " + x);

    if (x != 198)
        throw new TestFailure("Test failed (x != 198)");
  }
}
