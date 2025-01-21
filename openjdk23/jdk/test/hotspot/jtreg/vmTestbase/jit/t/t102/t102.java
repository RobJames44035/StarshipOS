/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t102.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t102.t102
 */

package jit.t.t102;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Like t101.java except the short intege type is short instead of char.

public class t102
{
    public static final GoldChecker goldChecker = new GoldChecker( "t102" );

    public static void main(String[] argv)
    {
        short a[] = new short[8];
        short x,x0,x1,x2,x3,x4,x5,x6,x7;
        int i;
        for(i=0; i<8; i+=1)
            a[i] = (short) (i - 4);
        x = (short) (
            (x0=(short)(int)a[0]) +
            ((x1=(short)(int)a[1]) +
            ((x2=(short)(int)a[2]) +
            ((x3=(short)(int)a[3]) +
            ((x4=(short)(int)a[4]) +
            ((x5=(short)(int)a[5]) +
            ((x6=(short)(int)a[6]) +
            (x7=(short)(int)a[7])))))))
            );

        t102.goldChecker.println("... and the answer is " + (int) x);
        t102.goldChecker.check();
    }
}
