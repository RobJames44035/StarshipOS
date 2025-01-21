/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t034.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t034.t034
 */

package jit.t.t034;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_idiv
// opc_imul
// opc_ishl
// opc_ishr
// opc_iushr
// opc_ladd
// opc_ldiv
// opc_lmul
// opc_lrem
// opc_lsub

public class t034
{
    public static final GoldChecker goldChecker = new GoldChecker( "t034" );

    public static void main(String argv[])
    {
        int i, j;
        long l, m;

        i = 3;
        j = 33;
        l = 402;
        m = 7;

        t034.goldChecker.println(i/j);
        t034.goldChecker.println(i*j);
        t034.goldChecker.println(i<<j);
        t034.goldChecker.println(i>>j);
        t034.goldChecker.println(i>>>j);
        t034.goldChecker.println(l+m);
        t034.goldChecker.println(l/m);
        t034.goldChecker.println(l*m);
        t034.goldChecker.println(l%m);
        t034.goldChecker.println(l-m);

        i = -i;
        l = -l;

        t034.goldChecker.println();
        t034.goldChecker.println(i/j);
        t034.goldChecker.println(i*j);
        t034.goldChecker.println(i<<j);
        t034.goldChecker.println(i>>j);
        t034.goldChecker.println(i>>>j);
        t034.goldChecker.println(l+m);
        t034.goldChecker.println(l/m);
        t034.goldChecker.println(l*m);
        t034.goldChecker.println(l%m);
        t034.goldChecker.println(l-m);

        t034.goldChecker.check();
    }
}
