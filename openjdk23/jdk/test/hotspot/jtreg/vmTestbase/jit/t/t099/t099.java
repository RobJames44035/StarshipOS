/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t099.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t099.t099
 */

package jit.t.t099;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t099
{
    public static final GoldChecker goldChecker = new GoldChecker( "t099" );

    static void foo(int a, int b, int c, int d, int e,
                    int f, int g, int h, int i, int j)
    {
        foo(a,b,c,d,e,f,g,h,i,j);
    }

    public static void main(String[] argv)
    {
        try
        {
            foo(1,2,3,4,5,6,7,8,9,10);
        }
        catch (Throwable t)
        {
            t099.goldChecker.println(t.toString() + " detected, thrown, and caught.");
        }
        t099.goldChecker.check();
    }
}
