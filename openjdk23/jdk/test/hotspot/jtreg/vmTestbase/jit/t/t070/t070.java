/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t070.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t070.t070
 */

package jit.t.t070;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Dup, dup_x2, and dup2_x2.

public class t070
{
    public static final GoldChecker goldChecker = new GoldChecker( "t070" );

    public static void main(String[] argv)
    {
        int ia[] = new int[2];
        long la[] = new long[2];
        int i, j;
        int x = 39, y = 42;

        ia[0] = ia[1] = x + y;
        la[1] = la[0] = x - y;
        t070.goldChecker.println(ia[0] + " " + ia[1] + " " + la[1] + " " + la[0]);

        i = 0;
        j = 1;

        ia[i] = ia[j] = x + y;
        la[j] = la[i] = x - y;
        t070.goldChecker.println(ia[i] + " " + ia[j] + " " + la[j] + " " + la[i]);
        t070.goldChecker.check();
    }
}
