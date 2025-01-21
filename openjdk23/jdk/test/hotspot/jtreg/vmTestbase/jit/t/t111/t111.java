/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t111.
 * VM Testbase keywords: [jit, quick]
 * VM Testbase readme:
 * Clone of t089.  The pass file changed in JDK 1.2.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build jit.t.t111.t111
 * @comment ExecDriver is used so golden file won't depend on jtreg
 * @run driver ExecDriver --java
 *      -Dtest.src=${test.src}
 *      jit.t.t111.t111
 */

package jit.t.t111;

import nsk.share.GoldChecker;

import java.io.PrintWriter;
import java.io.StringWriter;

// THIS TEST IS LINE NUMBER SENSITIVE

// Uncaught exception, one jit'd frame on the stack.

public class t111 {
    public static final GoldChecker goldChecker = new GoldChecker("t111");

    public static void main(String[] argv) {
        try {
            doit();
        } catch (Throwable t) {
            StringWriter sr = new StringWriter();
            t.printStackTrace(new PrintWriter(sr));
            t111.goldChecker.print(sr.toString().replace("\t", "        "));
        }
        t111.goldChecker.check();
    }

    public static void doit() throws Throwable {
        Throwable t = new Throwable();
        t.fillInStackTrace();
        throw t;
    }
}
