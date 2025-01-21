/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/overflow.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.overflow.overflow
 */

package jit.overflow;

/*
   This test checks if a JIT can still detect stack overflow. Method
   invocation overhead is expensive in Java and improving it is a
   nobel cause for a JIT. JITs just have to be careful that they
   don't loose some error handling ability in doing so.
*/

import java.lang.*;

import nsk.share.TestFailure;

public class overflow {
    public static void main(String[] args) {
        try {
           recurse(1);
        } catch (StackOverflowError e) {
           System.out.println("Test PASSES");
           return;
        }
        throw new TestFailure("Test FAILED");
    }

    static int recurse(int n) {
        if (n != 0) {
            return recurse(n+1);
        }
        return 0;
    }
}
