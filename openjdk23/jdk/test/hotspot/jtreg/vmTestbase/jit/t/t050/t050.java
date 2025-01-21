/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t050.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t050.t050
 */

package jit.t.t050;

import nsk.share.TestFailure;

// Pending local load clobbered by local store.

public class t050
{
    public static void main(String argv[])
    {
        int i;
        i = 39;
        i = i + (i = 42);
        if(i != 81)
                throw new TestFailure("i != 81 (" + i + ")");
    }
}
