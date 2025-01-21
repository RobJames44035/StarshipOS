/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 * @key randomness
 *
 * @summary converted from VM Testbase gc/gctests/gctest04.
 * VM Testbase keywords: [gc]
 *
 * @library /vmTestbase
 *          /test/lib
 * @compile reqgen.java
 * @run main/othervm gc.gctests.gctest04.gctest04
 */

package gc.gctests.gctest04;

import nsk.share.test.*;
import nsk.share.TestFailure;
//gctest04.java

import nsk.share.TestBug;
import nsk.share.TestFailure;


// small objects ( 8 ~ 32k), short live time ( 5 ~ 10 ms)
public class gctest04 {
  public static void main(String args[] )
  {
    int queueLimit = 1000;
    if (args.length > 0)
    {
        try
        {
            queueLimit = Integer.valueOf(args[0]).intValue();
        }
        catch (NumberFormatException e)
        {
            throw new TestBug("Bad input to gctest04. Expected integer, " +
                            " got: ->" + args[0] + "<-", e);
        }
    }


    queue  requestque = new queue(queueLimit);
    reqgen  gen = new reqgen(requestque, 5);
    gen.setsize(8, 32*1024);
    gen.setlive(5, 10);


    reqdisp disp = new reqdisp(requestque);
    gen.start();
    disp.start();

    try
    {
        gen.join();
        System.out.println("Joined with gen thread");
        disp.join();
        System.out.println("Joined with disp thread");
    }
    catch (InterruptedException e)
    {
        System.err.println("InterruptedException in gctest04.main()");
    }
        System.out.println("Test passed.");
  }
}
