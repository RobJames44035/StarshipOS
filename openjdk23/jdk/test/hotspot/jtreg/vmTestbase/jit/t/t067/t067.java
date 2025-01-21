/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t067.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t067.t067
 */

package jit.t.t067;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t067
{
  public static final GoldChecker goldChecker = new GoldChecker( "t067" );

  static int i = -1;

  static void chkCond(String s, boolean b)
  {
      t067.goldChecker.print(s);
      if(b)
          t067.goldChecker.println(" ok");
      else
          t067.goldChecker.println(" fubar");
  }

  private static void check_sign()
  {
   // dividend is positive
   //
     chkCond("err_1", (Integer.MAX_VALUE%Integer.MIN_VALUE)>=0);
     chkCond("err_2", (Integer.MAX_VALUE%i)>=0);
     chkCond("err_3", (1%Integer.MIN_VALUE)>=0);
     chkCond("err_4", (1%(-1))>=0);
     chkCond("err_5", (3%4)>=0);

   // dividend is negative
   //
     chkCond("err_6", (Integer.MIN_VALUE%Integer.MAX_VALUE)<=0);
     chkCond("err_7", (Integer.MIN_VALUE%1)<=0);

     // This one fails because the division raises some sort of
     // hardware arithmetic exception.  I guess the hardware notices
     // that it can't represent the result of 0x80000000 / -1 or
     // something.  Anyhow, the behavior is the same, jit or no jit.
     // If they ever get the VM fixed, I'll have to make the
     // corresponding fix to the jit.
     // chkCond("err_8", (Integer.MIN_VALUE%(i))<=0);

     chkCond("err_9", ((-1)%Integer.MAX_VALUE)<=0);
     chkCond("err_10", ((-1)%Integer.MIN_VALUE)<=0);
     chkCond("err_11", (-2)%(-1)<=0);
     chkCond("err_12", (-2)%(-3)<=0);

   // dividend is zero
   //
     chkCond("err_13", (0%Integer.MIN_VALUE)==0);
  }

  public static void main(String argv[])
  {
      t067.goldChecker.println("Integer.MIN_VALUE == " + Integer.MIN_VALUE);
      check_sign();
      t067.goldChecker.println("Miller time.");
      t067.goldChecker.check();
  }
}
