/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t018.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t018.t018
 */

package jit.t.t018;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_athrow

class jj extends Throwable
{
    String msg;

    jj(String s)
    {
        msg = s;
    }
}

public class t018
{
    public static final GoldChecker goldChecker = new GoldChecker( "t018" );

    public static void main(String argv[])
    {
        try
        {
            jj x = new jj("Hey dee ho.");
            throw x;
        }
        catch(jj x)
        {
            t018.goldChecker.println("Exception caught; msg is " + x.msg);
        }
    }
}
