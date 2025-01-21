/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
/*
   The init02.java test checks if a JIT changes the order in which
   classes are initialized. Java semantics do not allow a class to be
   initialized until it is actually used.
*/


/*
 * @test
 *
 * @summary converted from VM Testbase jit/init/init02.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.init.init02.init02
 */

package jit.init.init02;

import nsk.share.TestFailure;

public class init02 {
    public static boolean failed = false;
    public static void main(String args[]) {
        int i, x;
        for (i = 0; i < 10; i++) {
            x = i * 10;
            if (x < 0) {
                inittest.foo(x);
            }
        }

        if (failed)
            throw new TestFailure("\n\nInitializing inittest, test FAILS\n");
    }
}

class inittest {
    static {
        init02.failed = true;
    }

    public static void foo(int x) {
        System.out.println("foo value = " + x);
    }
}
