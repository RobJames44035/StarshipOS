/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t096.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t096.t096
 */

package jit.t.t096;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Empty synchronized methods.

public class t096
{
    public static final GoldChecker goldChecker = new GoldChecker( "t096" );

    static synchronized void foo(){}
    synchronized void bar(){}
    public static void main(String[] argv)
    {
        int i;
        t096 o;
        t096.goldChecker.println("I'm running.");
        o = new t096();
        for(i=0; i<2; i+=1)
        {
            foo();
            o.bar();
        }
        t096.goldChecker.println("I've stopped.");
        t096.goldChecker.check();
    }
}
