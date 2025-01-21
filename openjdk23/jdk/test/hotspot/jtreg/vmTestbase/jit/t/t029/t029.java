/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t029.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t029.t029
 */

package jit.t.t029;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_drem, opc_frem, opc_dreturn opc_freturn

public class t029
{
    public static final GoldChecker goldChecker = new GoldChecker( "t029" );

    static float frem(float f, float g)
    {
        return f % g;
    }

    static double drem(double d, double e)
    {
        return d % e;
    }

    public static void main(String argv[])
    {
        double d = 39.12345, e = 402.001;
        float f = 39.12345f, g = 402.001f;
        double x;
        float y;

        // Both pos.

        t029.goldChecker.println();
        t029.goldChecker.println(d%e);
        t029.goldChecker.println(e%(x=d));
        t029.goldChecker.println(f%g);
        t029.goldChecker.println(g%(y=f));

        d = -d;
        f = -f;

        // First neg; second pos.

        t029.goldChecker.println();
        t029.goldChecker.println(d%e);
        t029.goldChecker.println(e%(x=d));
        t029.goldChecker.println(f%g);
        t029.goldChecker.println(g%(y=f));

        e = -e;
        g = -g;

        // Both neg.

        t029.goldChecker.println();
        t029.goldChecker.println(d%e);
        t029.goldChecker.println(e%(x=d));
        t029.goldChecker.println(f%g);
        t029.goldChecker.println(g%(y=f));

        d = -d;
        f = -f;

        // First pos; second neg;

        t029.goldChecker.println();
        t029.goldChecker.println(d%e);
        t029.goldChecker.println(e%(x=d));
        t029.goldChecker.println(f%g);
        t029.goldChecker.println(g%(y=f));

        e = -e;
        g = -g;

        // Both pos.

        t029.goldChecker.println();
        t029.goldChecker.println(drem(d,e));
        t029.goldChecker.println(drem(e, x=d));
        t029.goldChecker.println(frem(f, g));
        t029.goldChecker.println(frem(g, y=f));

        d = -d;
        f = -f;

        // First neg; second pos.

        t029.goldChecker.println();
        t029.goldChecker.println(drem(d,e));
        t029.goldChecker.println(drem(e, x=d));
        t029.goldChecker.println(frem(f, g));
        t029.goldChecker.println(frem(g, y=f));

        e = -e;
        g = -g;

        // Both neg.

        t029.goldChecker.println();
        t029.goldChecker.println(drem(d,e));
        t029.goldChecker.println(drem(e, x=d));
        t029.goldChecker.println(frem(f, g));
        t029.goldChecker.println(frem(g, y=f));

        d = -d;
        f = -f;

        // First pos; second neg.

        t029.goldChecker.println();
        t029.goldChecker.println(drem(d,e));
        t029.goldChecker.println(drem(e, x=d));
        t029.goldChecker.println(frem(f, g));
        t029.goldChecker.println(frem(g, y=f));
        t029.goldChecker.check();
    }
}
