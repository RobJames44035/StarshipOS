/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t022.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t022.t022
 */

package jit.t.t022;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// The double-precision version of t021.java.

public class t022
{
    public static final GoldChecker goldChecker = new GoldChecker( "t022" );

    public static void main(String argv[])
    {
        double a, b, c, d, e;

        a = 0.0;
        b = 1.0;
        c = 2.0;
        e = 41.0 - c;
        d = a + b * c / e;

        t022.goldChecker.println(a);
        t022.goldChecker.println(b);
        t022.goldChecker.println(c);
        t022.goldChecker.println(d);
        t022.goldChecker.check();
    }
}
