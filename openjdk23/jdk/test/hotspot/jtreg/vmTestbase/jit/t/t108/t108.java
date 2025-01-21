/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t108.
 * VM Testbase keywords: [jit, quick]
 * VM Testbase readme:
 * Clone of t083.  The pass file changed in JDK 1.2.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build jit.t.t108.t108
 * @comment ExecDriver is used so golden file won't depend on jtreg
 * @run driver ExecDriver --java
 *      -Dtest.src=${test.src}
 *      jit.t.t108.t108
 */

package jit.t.t108;

import java.io.*;
import nsk.share.GoldChecker;

// THIS TEST IS LINE NUMBER SENSITIVE

// Uncaught exception, one jit'd frame on the stack.

public class t108 {
    public static final GoldChecker goldChecker = new GoldChecker( "t108" );

    public static void main(String[] argv) {
        try {
            doit();
        } catch (Throwable t) {
            StringWriter sr = new StringWriter();
            t.printStackTrace(new PrintWriter(sr));
            t108.goldChecker.print(sr.toString().replace("\t", "        "));
        }
        t108.goldChecker.check();
    }

    static void doit() throws Throwable {
        Throwable t = new Throwable();
        throw t;
    }
}
