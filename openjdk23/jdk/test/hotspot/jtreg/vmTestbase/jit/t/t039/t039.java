/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t039.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t039.t039
 */

package jit.t.t039;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_lreturn, opc_monitorenter, opc_monitorexit

public class t039
{
    public static final GoldChecker goldChecker = new GoldChecker( "t039" );

    private static long f0 = 0, f1 = 1;
    private static Object x = new Object();

    private static long nextFib()
    {
        long res;

        synchronized(x)
        {
            res = f0 + f1;
            f0 = f1;
            f1 = res;
        }
        return res;
    }

    public static void main(String argv[])
    {
        for(int i=2; i<10; ++i)
            t039.goldChecker.println(i + ": " + nextFib());
        t039.goldChecker.check();
    }
}
