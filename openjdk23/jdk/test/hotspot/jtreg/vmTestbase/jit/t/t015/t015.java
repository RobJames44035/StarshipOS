/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t015.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t015.t015
 */

package jit.t.t015;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t015
{
    public static final GoldChecker goldChecker = new GoldChecker( "t015" );

    int x;

    t015(int i)
    {
        x = i;
    }


    public static void main(String argv[])
    {
        t015 a[] = new t015[2];
        int i;

        t015.goldChecker.println("Cookin' ...");
        for(i=0; i<2; i+=1)
            a[i] = null;

        t015.goldChecker.println("Pass 1 checking ...");
        for(i=0; i<2; i+=1)
            if(a[i] != null)
                t015.goldChecker.println("Fubar at " + i + " on pass 1");

        t015.goldChecker.println("Initializing elements ...");
        for(i=0; i<2; i+=1)
            a[i] = new t015(i);

        t015.goldChecker.println("Pass 2 checking ...");
        for(i=0; i<2; i+=1)
        {
            if(a[i] == null)
                t015.goldChecker.println("Null at " + i + " on pass 2");
            else if(a[i].x != i)
                t015.goldChecker.println("a[i].x != i at " + i + " on pass 2");
        }

        t015.goldChecker.println("Except as noted above, I'm a happy camper.");
        t015.goldChecker.check();
    }
}
