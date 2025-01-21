/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/Sleeper.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.Sleeper.Sleeper
 */

package jit.Sleeper;

import nsk.share.TestFailure;

public class Sleeper {
    public static void main(String args[] ) {
      System.out.println ("1");
      try { Thread.sleep(1000); } catch (InterruptedException e) {}
      System.out.println ("2");
      try { Thread.sleep(1000); } catch (InterruptedException e) {}
      System.out.println ("3");
      try { Thread.sleep(1000); } catch (InterruptedException e) {}
      System.out.println ("4");
    }
}
