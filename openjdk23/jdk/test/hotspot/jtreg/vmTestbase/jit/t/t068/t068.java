/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t068.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t068.t068
 */

package jit.t.t068;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t068
{
    public static final GoldChecker goldChecker = new GoldChecker( "t068" );

    static byte s1,s2,s3,s4;
    byte i1,i2,i3,i4;
    public static void main(String[] argv)
    {
        t068 o = new t068();
        s4 = 4;
        s3 = 3;
        s2 = 2;
        s1 = 1;
        o.i4 = 4;
        o.i3 = 3;
        o.i2 = 2;
        o.i1 = 1;
        t068.goldChecker.println(s1);
        t068.goldChecker.println(o.i1);
        t068.goldChecker.check();
    }
}
