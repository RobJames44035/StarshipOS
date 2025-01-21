/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t079.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t079.t079
 */

package jit.t.t079;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t079
{
    public static final GoldChecker goldChecker = new GoldChecker( "t079" );

    public static void main(String[] argv)
    {
        double d[] = new double[2];
        double e[] = null;
        double x = 39.0, y = 42.0;
        double z;
        int i = 0, j = 1;
        int r;
        (e = d)[r = i + j] = (z = x + y);
        t079.goldChecker.println(e[1]);
        t079.goldChecker.check();
    }
}
