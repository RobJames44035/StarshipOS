/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t066.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t066.t066
 */

package jit.t.t066;

import nsk.share.TestFailure;

// Time was when j86MakeDoubleUsable was screwing up the
// offsets on the stores of the two halves of the double
// constant.

public class t066
{
    public static void main(String argv[])
    {
        float f;

        f = (float) (-1 * 1 * Math.pow(2.0, -150.0));
        if(f != -0.0)
                throw new TestFailure("f != -0.0 (" + f + ")");
    }
}
