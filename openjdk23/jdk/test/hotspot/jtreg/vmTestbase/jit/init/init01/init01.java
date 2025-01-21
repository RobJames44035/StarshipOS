/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
// testing correct initialization order


/*
 * @test
 *
 * @summary converted from VM Testbase jit/init/init01.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.init.init01.init01
 */

package jit.init.init01;

import nsk.share.TestFailure;

class InitTest1 {
  static int ix1 = 0;
  int i_ix;
  InitTest1 () {
    i_ix = ix1;
  }
}

class InitTest2 {
  static int ix2;
  static InitTest1 oop = new InitTest1();
}

public class init01 {


  public static void main (String s[]) {
        InitTest1.ix1 = 5445;
        InitTest2.ix2 = 1;
        if (InitTest2.oop.i_ix == 5445)
           System.out.println ("Correct order of initialization");
        else
           throw new TestFailure("Incorrect order of initialization");
  }

}
