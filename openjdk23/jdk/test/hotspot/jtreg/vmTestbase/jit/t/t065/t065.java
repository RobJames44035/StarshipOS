/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t065.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t065.t065
 */

package jit.t.t065;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Main() does getfields of k.b and putfields of l.b.  K.set() does putfields
// of k.b; l.show() does getfields of l.b.  The idea is, you jit only
// main.  If the jit and the VM agree about the container size of a static
// field of type byte, you get the right answers.  If not, the test fails.
class k
{
    byte b;
    int i = -129;
    void set()
    {
        b = (byte) i;
        ++i;
    }
}

class l
{
    byte b;
    int i = -129;
    void show()
    {
        t065.goldChecker.println("lo.b == " + b);
    }
}

public class t065
{
    public static final GoldChecker goldChecker = new GoldChecker( "t065" );

    public static void main(String argv[])
    {
        k ko = new k();
        l lo = new l();
        int i;
        for(i=0; i<258; i+=1)
        {
            ko.set();
            t065.goldChecker.println("ko.b == " + ko.b);
            lo.b = (byte) lo.i;
            ++lo.i;
            lo.show();
        }
        t065.goldChecker.check();
    }
}
