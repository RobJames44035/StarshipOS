/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t020.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t020.t020
 */

package jit.t.t020;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_checkcast, opc_instanceof, opc_ifnull, opc_ifnonnull

public class t020
{
    public static final GoldChecker goldChecker = new GoldChecker( "t020" );

    private static void show(t020 x)
    {
        try
        {
            t020.goldChecker.print(" is a t020");
            if(x instanceof k)
                t020.goldChecker.print(" and also a k");
            if(x == null)
                t020.goldChecker.print(" and also null");
            if(x != null)
                t020.goldChecker.print(" and also not null");
            t020.goldChecker.println(".");
        }
        catch(Throwable e)
        {
            t020.goldChecker.println(" " + e.getMessage());
        }
    }

    public static void main(String argv[])
    {
        t020 a[] = new t020[4];
        t020 t = new k();
        k u = null;
        a[0] = new t020();
        a[1] = t;
        a[3] = u;

        for(int i=0; i<4; i+=1)
        {
            t020.goldChecker.print("a[" + i + "]");
            show(a[i]);
        }

        t020.goldChecker.println();
        for(int i=0; i<4; i+=1)
        {
            try
            {
                t020.goldChecker.print("Assigning a[" + i + "] to u");
                u = (k) a[i];
                t020.goldChecker.print(" worked ok");
            }
            catch(Throwable x)
            {
                t020.goldChecker.print(" failed");
            }
            finally
            {
                t020.goldChecker.println(".");
            }
        }
        t020.goldChecker.check();
    }

}

class k extends t020
{
}
