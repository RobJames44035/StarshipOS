/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t094.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t094.t094
 */

package jit.t.t094;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t094
{
    public static final GoldChecker goldChecker = new GoldChecker( "t094" );

    static int i, j;

    static void foo()
    {
        i /= j;
    }

    public static void main(String argv[])
    {
        try
        {
            foo();
        }
        catch(Throwable x)
        {
            t094.goldChecker.println("Hiya.");
        }
        try
        {
            foo();
        }
        catch(Throwable x)
        {
            t094.goldChecker.println("Bye.");
        }
        t094.goldChecker.check();
    }
}
