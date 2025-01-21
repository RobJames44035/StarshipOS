/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t038.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t038.t038
 */

package jit.t.t038;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_laload, opc_lastore, opc_lconst_0

public class t038
{
    public static final GoldChecker goldChecker = new GoldChecker( "t038" );

    public static void main(String argv[])
    {
        long a[] = new long[10];
        int i;
        a[0] = 0l;
        a[1] = 1l;
        for(i = 2; i < 10; i++)
            a[i] = a[i-1] + a[i-2];
        i = 0;
        do
            t038.goldChecker.println(a[i++]);
        while(i < 10);
        t038.goldChecker.check();
    }
}
