/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t100.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t100.t100
 */

package jit.t.t100;

/*
   This test check it a JIT can still detect stack overflow. Method
   invocation overhead is expensive in Java and improving it is a
   nobel cause for a JIT. JITs just have to be careful that they
   don't loose some error handling ability in doing so.
*/

import java.lang.*;
import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t100 {
    public static final GoldChecker goldChecker = new GoldChecker( "t100" );

    public static void main(String[] args) {
        try {
           recurse(1);
        } catch (StackOverflowError e) {
           t100.goldChecker.println("Test PASSES");
        }
        t100.goldChecker.check();
    }

    static int recurse(int n) {
        if (n != 0) {
            return recurse(n+1);
        }
        return 0;
    }
}
