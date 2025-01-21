/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t012.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t012.t012
 */

package jit.t.t012;

import nsk.share.TestFailure;

public class t012
{
    public static void main(String argv[])
    {
        if(fib(20) != 6765)
                throw new TestFailure("fib(20) != 6765");
    }

    static int fib(int n)
    {
        int res;
        if(n <= 0)
            res = 0;
        else if(n == 1)
            res = 1;
        else
            res = fib(n-2) + fib(n-1);
        return res;
    }
}
