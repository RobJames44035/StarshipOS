/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t069.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t069.t069
 */

package jit.t.t069;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Dup, dup_x2, and dup2_x2.

public class t069
{
    public static final GoldChecker goldChecker = new GoldChecker( "t069" );

    public static void main(String[] argv)
    {
        int ia[] = new int[2];
        long la[] = new long[2];
        int i, j;

        ia[0] = ia[1] = 39;
        la[1] = la[0] = 42;
        t069.goldChecker.println(ia[0] + " " + ia[1] + " " + la[1] + " " + la[0]);

        i = 0;
        j = 1;

        ia[i] = ia[j] = 39;
        la[j] = la[i] = 42;
        t069.goldChecker.println(ia[i] + " " + ia[j] + " " + la[j] + " " + la[i]);
        t069.goldChecker.check();
    }
}
