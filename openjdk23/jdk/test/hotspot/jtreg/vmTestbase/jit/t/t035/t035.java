/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t035.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t035.t035
 */

package jit.t.t035;

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

public class t035
{
    public static final GoldChecker goldChecker = new GoldChecker( "t035" );

    public static void main(String argv[])
    {
        int i, j;
        long l, m;

        i = 3;
        j = 33;
        l = 402;
        m = 1;

        t035.goldChecker.println(i/j);
        t035.goldChecker.println(i*j);
        t035.goldChecker.println(i<<j);
        t035.goldChecker.println(i>>j);
        t035.goldChecker.println(i>>>j);
        t035.goldChecker.println(l+m);
        t035.goldChecker.println(l/m);
        t035.goldChecker.println(l*m);
        t035.goldChecker.println(l%m);
        t035.goldChecker.println(l-m);

        i = -i;
        l = -l;

        t035.goldChecker.println();
        t035.goldChecker.println(i/j);
        t035.goldChecker.println(i*j);
        t035.goldChecker.println(i<<j);
        t035.goldChecker.println(i>>j);
        t035.goldChecker.println(i>>>j);
        t035.goldChecker.println(l+m);
        t035.goldChecker.println(l/m);
        t035.goldChecker.println(l*m);
        t035.goldChecker.println(l%m);
        t035.goldChecker.println(l-m);

        t035.goldChecker.check();
    }
}
