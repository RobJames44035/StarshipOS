/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t049.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t049.t049
 */

package jit.t.t049;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Register jams and spills

public class t049
{
    public static final GoldChecker goldChecker = new GoldChecker( "t049" );

    static void intRems()
    {
        int a, b, c, d, e, f, g, h, i, j, k, l, z;

        a=1; b=2; c=3; d=4; e=5; f=6; g=7; h=8; i=9; j=10; k=11; l=12;
        z = (a%b) % ((c%d) % ((e%f) % ((g%h) % ((i%j) % (k%l)))));
        t049.goldChecker.println("Int: " + z);
    }

    static void longRems()
    {
        long a, b, c, d, e, f, g, h, i, j, k, l, z;

        a=1; b=2; c=3; d=4; e=5; f=6; g=7; h=8; i=9; j=10; k=11; l=12;
        z = (a%b) % ((c%d) % ((e%f) % ((g%h) % ((i%j) % (k%l)))));
        t049.goldChecker.println("Long: " + z);
    }

    static void floatRems()
    {
        float a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, z;

        a=1; b=2; c=3; d=4; e=5; f=6; g=7; h=8; i=9; j=10; k=11; l=12;
        m=13; n=14; o=15; p=16; q=17; r=18; s=19; t=20;
        z = (a%b) % ((c%d) % ((e%f) % ((g%h) % ((i%j) % ((k%l) % ((m%n) %
            ((o%p) % ((q%r) % (s%t)))))))));
        t049.goldChecker.println("Float: " + z);
    }

    static void doubleRems()
    {
        double a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, z;

        a=1; b=2; c=3; d=4; e=5; f=6; g=7; h=8; i=9; j=10; k=11; l=12;
        m=13; n=14; o=15; p=16; q=17; r=18; s=19; t=20;
        z = (a%b) % ((c%d) % ((e%f) % ((g%h) % ((i%j) % ((k%l) % ((m%n) %
            ((o%p) % ((q%r) % (s%t)))))))));
        t049.goldChecker.println("Double: " + z);
    }

    public static void main(String argv[])
    {
        intRems();
        longRems();
        floatRems();
        doubleRems();
        t049.goldChecker.check();
    }
}
