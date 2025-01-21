/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t003.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t003.t003
 */

package jit.t.t003;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t003
{
    public static final GoldChecker goldChecker = new GoldChecker( "t003" );

    public static void main(String argv[])
    {
        int a[] = new int[2];
        int i = -1;

        a[0] = 39;
        a[1] = 40;
        t003.goldChecker.println("a[0] == " + a[0]);
        t003.goldChecker.println("a[1] == " + a[1]);

        try
        {
            a[2] = 41;
            t003.goldChecker.println("o-o-r high didn't throw exception");
        }
        catch(Throwable x)
        {
            t003.goldChecker.println("o-o-r high threw exception");
        }

        try
        {
            a[i] = 41;
            t003.goldChecker.println("o-o-r low didn't throw exception");
        }
        catch(Throwable x)
        {
            t003.goldChecker.println("o-o-r low threw exception");
        }
        t003.goldChecker.check();
    }
}
