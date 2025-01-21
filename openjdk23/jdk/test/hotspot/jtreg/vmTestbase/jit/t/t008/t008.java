/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t008.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t008.t008
 */

package jit.t.t008;

import nsk.share.TestFailure;

public class t008
{
    public static void main(String argv[])
    {
        int i;
        i = 39;
        int result;
        switch(i)
        {
        case 30: result = 30; break;
        case 39: result = 31; break;
        case 402: result = 32; break;
        case 1033: result = 33; break;
        case 2034: result = 34; break;
        case 2135: result = 35; break;
        case 35036: result = 36; break;
        case 35037: result = 37; break;
        case 100038: result = 38; break;
        case 200039: result = 39; break;
        case 300040: result = 40; break;
        case 400041: result = 41; break;
        case 500042: result = 42; break;
        case 600043: result = 43; break;
        case 700044: result = 44; break;
        case 800045: result = 45; break;
        case 900046: result = 46; break;
        default: result = -1; break;
        }

        if(result != 31)
                throw new TestFailure("Test failed: result != 31 (" + result + ")");
    }
}
