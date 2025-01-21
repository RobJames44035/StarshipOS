/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t086.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t086.t086
 */

package jit.t.t086;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Exception at patch time.

class foo
{
    static int i;
    static int j;
    static
    {
        i = 0;
        j = 409;
        j /= i;
    }

    static void heydee()
    {
        t086.goldChecker.println("Heydee ho.");
    }
}

public class t086
{
    public static final GoldChecker goldChecker = new GoldChecker( "t086" );

    public static void main(String argv[])
    {
        t086.goldChecker.println("Hi.");
        try
        {
            foo.heydee();
        }
        catch(Throwable t)
        {
            t086.goldChecker.println("Caught exception.");
        }
        t086.goldChecker.println("G'bye.");
        t086.goldChecker.check();
    }
}
