/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t040.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t040.t040
 */

package jit.t.t040;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;


// opc_multianewarray

public class t040
{
    public static final GoldChecker goldChecker = new GoldChecker( "t040" );

    private static int id(int i)
    {
        return i;
    }
    private static double sqrt(double x)
    {
        double q;
        double qsquared;
        if(x == 0.0)
            return 0.0;
        q = x;
        for(int i=0; i<6; i+=1)
        {
            qsquared = q * q;
            q += (x - qsquared) / (2.0 * q);
        }
        return q;
    }

    private static double abs(double x)
    {
        return x >= 0.0 ? x : -x;
    }

    public static void main(String argv[])
    {
        int c[][][] = null;

        c = new int[id(2)][id(2)][id(2)];
        for(int i1=id(0); i1<id(2); i1+=id(1))
        {
        for(int j1=id(0); j1<id(2); j1+=id(1))
        {
            for(int i2=id(0); i2<id(2); i2+=id(1))
            {
            for(int j2=id(0); j2<id(2); j2+=id(1))
            {
                for(int i3=id(0); i3<id(2); i3+=id(1))
                {
                for(int j3=id(0); j3<id(2); j3+=id(1))
                {
                    double d;
                    int d1,d2,d3;
                    t040.goldChecker.print(i1 + "," + i2 + "," + i3 + " <-> ");
                    t040.goldChecker.print(j1 + "," + j2 + "," + j3 +": ");
                    d1 = j1 - i1;
                    d2 = j2 - i2;
                    d3 = j3 - i3;
                    d = sqrt(d1*d1 + d2*d2 + d3*d3);
                    t040.goldChecker.println(d);
                }
                }
            }
            }
        }
        }
        t040.goldChecker.check();
    }
}
