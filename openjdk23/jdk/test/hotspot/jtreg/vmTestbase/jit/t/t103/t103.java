/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t103.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t103.t103
 */

package jit.t.t103;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Like t101.java except the short intege type is byte instead of char.

public class t103
{
    public static final GoldChecker goldChecker = new GoldChecker( "t103" );

    public static void main(String[] argv)
    {
        byte a[] = new byte[8];
        byte x,x0,x1,x2,x3,x4,x5,x6,x7;
        int i;
        for(i=0; i<8; i+=1)
            a[i] = (byte) (i - 1);
        x = (byte) (
            (x0=(byte)(int)a[0]) +
            ((x1=(byte)(int)a[1]) +
            ((x2=(byte)(int)a[2]) +
            ((x3=(byte)(int)a[3]) +
            ((x4=(byte)(int)a[4]) +
            ((x5=(byte)(int)a[5]) +
            ((x6=(byte)(int)a[6]) +
            (x7=(byte)(int)a[7])))))))
            );

        t103.goldChecker.println("... and the answer is " + (int) x);
        t103.goldChecker.check();
    }
}
