/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t071.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t071.t071
 */

package jit.t.t071;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t071
{
    public static final GoldChecker goldChecker = new GoldChecker( "t071" );

    public static void main(String[] argv)
    {
        float ia[] = new float[2];
        double la[] = new double[2];
        int i, j;

        ia[0] = ia[1] = 39.0f;
        la[1] = la[0] = 42.0;
        t071.goldChecker.println(ia[0] + " " + ia[1] + " " + la[1] + " " + la[0]);

        i = 0;
        j = 1;

        ia[i] = ia[j] = 39.0f;
        la[j] = la[i] = 42.0;
        t071.goldChecker.println(ia[i] + " " + ia[j] + " " + la[j] + " " + la[i]);
        t071.goldChecker.check();
    }
}
