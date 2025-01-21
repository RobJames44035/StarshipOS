/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t033.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t033.t033
 */

package jit.t.t033;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_iand, opc_ior, opc_ixor
// opc_land, opc_lor, opc_lxor

public class t033
{
    public static final GoldChecker goldChecker = new GoldChecker( "t033" );

    int i = 39;
    int j = 12;
    long l = 39;
    long m = 12;

    void doit()
    {
        t033.goldChecker.println(i&j);
        t033.goldChecker.println(i|j);
        t033.goldChecker.println(i^j);
        t033.goldChecker.println(l&m);
        t033.goldChecker.println(l|m);
        t033.goldChecker.println(l^m);
    }

    public static void main(String argv[])
    {
        t033 o = new t033();
        o.doit();
        t033.goldChecker.check();
    }
}
