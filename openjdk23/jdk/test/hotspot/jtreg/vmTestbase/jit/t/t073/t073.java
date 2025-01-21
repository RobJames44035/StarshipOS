/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t073.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t073.t073
 */

package jit.t.t073;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t073{
    public static final GoldChecker goldChecker = new GoldChecker( "t073" );

    static int i0 = 0;
    static int i1 = 1;
    static int i2 = 2;
    static int i3 = 3;
    static int i4 = 4;
    static int i5 = 5;
    static int i6 = 6;
    static int i7 = 7;

    public static void main(String[] argv){
        int a;
        a =
        (((i0 + i1) + (i2 + i3)) + ((i4 + i5) + (i6 + i7)));
        t073.goldChecker.println(a);
        t073.goldChecker.check();
    }
}
