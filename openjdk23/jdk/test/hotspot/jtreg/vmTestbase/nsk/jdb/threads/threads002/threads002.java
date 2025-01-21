/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jdb/threads/threads002.
 * VM Testbase keywords: [jpda, jdb]
 * VM Testbase readme:
 * DECSRIPTION
 *  This is a test for jdb 'threads' command.
 *  The debugee starts 5 'MyThreads' that are all suspended on the lock
 *  that the main thread posseses. The 'threads' command is issued
 *  at this point. The test passes if 5 suspended 'MyThreads' are reported.
 * COMMENTS
 *  This test functionally equals to nsk/jdb/threads/threads001 test
 *  and replaces it.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jdb.threads.threads002.threads002a
 * @run driver
 *      nsk.jdb.threads.threads002.threads002
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -verbose
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -jdb=${test.jdk}/bin/jdb
 *      -java.options="${test.vm.opts} ${test.java.opts}"
 *      -workdir=.
 *      -jdb.option="-trackallthreads"
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package nsk.jdb.threads.threads002;

import nsk.share.*;
import nsk.share.jdb.*;

import java.io.*;
import java.util.*;

public class threads002 extends JdbTest {

    public static void main (String argv[]) {
        debuggeeClass =  DEBUGGEE_CLASS;
        firstBreak = FIRST_BREAK;
        lastBreak = LAST_BREAK;
        new threads002().runTest(argv);
    }

    static final String PACKAGE_NAME     = "nsk.jdb.threads.threads002";
    static final String TEST_CLASS       = PACKAGE_NAME + ".threads002";
    static final String DEBUGGEE_CLASS   = TEST_CLASS + "a";
    static final String FIRST_BREAK      = DEBUGGEE_CLASS + ".main";
    static final String LAST_BREAK       = DEBUGGEE_CLASS + ".lastBreak";

    static final String THREAD_NAME      = PACKAGE_NAME + ".MyThread";

    protected void runCases() {
        String[] reply;
        Paragrep grep;
        int count;
        Vector v;
        String found;

        jdb.setBreakpointInMethod(LAST_BREAK);
        jdb.receiveReplyFor(JdbCommand.cont);

        reply = jdb.receiveReplyFor(JdbCommand.threads);
        grep = new Paragrep(reply);
        count = grep.find(THREAD_NAME);
        if (count != threads002a.numThreads ) {
            failure("Unexpected number of " + THREAD_NAME + " was listed: " + count +
                "\n\texpected value: " + threads002a.numThreads);
        }

        jdb.contToExit(1);
    }
}
