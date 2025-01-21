/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t025.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t025.t025
 */

package jit.t.t025;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// opc_daload, opc_dastore, opc_faload, opc_fastore

public class t025
{
    public static final GoldChecker goldChecker = new GoldChecker( "t025" );

    static void show(double d[], float f[])
    {
        for(int i=0; i<10; i+=1)
            t025.goldChecker.println(i + ": " + f[i] + ", " + d[i]);
    }

    public static void main(String argv[])
    {
        double d[] = new double[10];
        float f[] = new float[10];
        d[0] = f[0] = 0.0f;
        d[1] = f[1] = 1.0f;
        for(int i=2; i<10; i+=1)
            d[i] = f[i] = f[i-1] + f[i-2];
        show(d, f);
        t025.goldChecker.check();
    }
}
