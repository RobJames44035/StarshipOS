/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t109.
 * VM Testbase keywords: [jit, quick]
 * VM Testbase readme:
 * Clone of t084.  The pass file changed in JDK 1.2.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build jit.t.t109.t109
 * @comment ExecDriver is used so golden file won't depend on jtreg
 * @run driver ExecDriver --java
 *      -Dtest.src=${test.src}
 *      jit.t.t109.t109
 */

package jit.t.t109;

import nsk.share.GoldChecker;

import java.io.PrintWriter;
import java.io.StringWriter;

// THIS TEST IS LINE NUMBER SENSITIVE

// Uncaught exception, one jit'd frame on the stack, implicit exception.

public class t109 {
    public static final GoldChecker goldChecker = new GoldChecker("t109");

    public static void main(String[] argv) {
        try {
            doit();
        } catch (Throwable t) {
            StringWriter sr = new StringWriter();
            t.printStackTrace(new PrintWriter(sr));
            t109.goldChecker.print(sr.toString().replace("\t", "        "));
        }
        t109.goldChecker.check();
    }

    public static void doit() {
        int i = 0;
        int j = 39;
        j /= i;
    }
}
