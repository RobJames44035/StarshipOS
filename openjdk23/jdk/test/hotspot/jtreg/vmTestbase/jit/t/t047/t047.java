/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t047.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t047.t047
 */

package jit.t.t047;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Register jams and spills

public class t047
{
    public static final GoldChecker goldChecker = new GoldChecker( "t047" );

    static void intMuls()
    {
        int a, b, c, d, e, f, g, h, i, j, k, l, z;

        a=1; b=2; c=3; d=4; e=5; f=6; g=7; h=8; i=9; j=10; k=11; l=12;
        z = (a*b) * ((c*d) * ((e*f) * ((g*h) * ((i*j) * (k*l)))));
        t047.goldChecker.println("Int: " + z);
    }

    static void longMuls()
    {
        long a, b, c, d, e, f, g, h, i, j, k, l, z;

        a=1; b=2; c=3; d=4; e=5; f=6; g=7; h=8; i=9; j=10; k=11; l=12;
        z = (a*b) * ((c*d) * ((e*f) * ((g*h) * ((i*j) * (k*l)))));
        t047.goldChecker.println("Long: " + z);
    }

    static void floatMuls()
    {
        float a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, z;

        a=1; b=2; c=3; d=4; e=5; f=6; g=7; h=8; i=9; j=10; k=11; l=12;
        m=13; n=14; o=15; p=16; q=17; r=18; s=19; t=20;
        z = (a*b) * ((c*d) * ((e*f) * ((g*h) * ((i*j) * ((k*l) * ((m*n) *
            ((o*p) * ((q*r) * (s*t)))))))));
        t047.goldChecker.println("Float: " + z);
    }

    static void doubleMuls()
    {
        double a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, z;

        a=1; b=2; c=3; d=4; e=5; f=6; g=7; h=8; i=9; j=10; k=11; l=12;
        m=13; n=14; o=15; p=16; q=17; r=18; s=19; t=20;
        z = (a*b) * ((c*d) * ((e*f) * ((g*h) * ((i*j) * ((k*l) * ((m*n) *
            ((o*p) * ((q*r) * (s*t)))))))));
        t047.goldChecker.println("Double: " + z);
    }

    public static void main(String argv[])
    {
        intMuls();
        longMuls();
        floatMuls();
        doubleMuls();
        t047.goldChecker.check();
    }
}
