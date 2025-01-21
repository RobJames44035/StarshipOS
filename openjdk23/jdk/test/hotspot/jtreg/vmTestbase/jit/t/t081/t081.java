/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t081.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t081.t081
 */

package jit.t.t081;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t081
{
    public static final GoldChecker goldChecker = new GoldChecker( "t081" );

    public static void main(String argv[])
    {
        int i = 0x87654321;
        int i1, i31, i32, i63;
        i1 = 1;
        i31 = 31;
        i32 = 32;
        i63 = 63;

        t081.goldChecker.println(i >> 1);
        t081.goldChecker.println(i >> 31);
        t081.goldChecker.println(i >> 32);
        t081.goldChecker.println(i >> 63);

        t081.goldChecker.println("");
        t081.goldChecker.println(i << 1);
        t081.goldChecker.println(i << 31);
        t081.goldChecker.println(i << 32);
        t081.goldChecker.println(i << 63);

        t081.goldChecker.println("");
        t081.goldChecker.println(i >>> 1);
        t081.goldChecker.println(i >>> 31);
        t081.goldChecker.println(i >>> 32);
        t081.goldChecker.println(i >>> 63);

        t081.goldChecker.println("");
        t081.goldChecker.println(i >> i1);
        t081.goldChecker.println(i >> i31);
        t081.goldChecker.println(i >> i32);
        t081.goldChecker.println(i >> i63);

        t081.goldChecker.println("");
        t081.goldChecker.println(i << i1);
        t081.goldChecker.println(i << i31);
        t081.goldChecker.println(i << i32);
        t081.goldChecker.println(i << i63);

        t081.goldChecker.println("");
        t081.goldChecker.println(i >>> i1);
        t081.goldChecker.println(i >>> i31);
        t081.goldChecker.println(i >>> i32);
        t081.goldChecker.println(i >>> i63);

        t081.goldChecker.check();
    }
}
