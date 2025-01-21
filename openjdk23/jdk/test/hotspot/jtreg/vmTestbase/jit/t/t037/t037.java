/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t037.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t037.t037
 */

package jit.t.t037;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_invokeinterface

interface foo
{
    void doit();
}

public class t037 implements foo
{
    public static final GoldChecker goldChecker = new GoldChecker( "t037" );

    public void doit()
    {
        t037.goldChecker.println("Hey dee ho from Lilliput.");
    }

    public static void main(String argv[])
    {
        foo o = new t037();
        o.doit();
        t037.goldChecker.check();
    }
}
