/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jdb/threadgroups/threadgroups002.
 * VM Testbase keywords: [jpda, jdb]
 * VM Testbase readme:
 * DECSRIPTION
 *  This is a test for jdb 'threadgroups' command.
 *  The main thread creates 3 threadgroups of 5 threads each.
 *  All threads are locked in their 'run' method on a lock that the main
 *  thread holds. The 'threadgroups' command is issued at this point.
 *  The test passes if three user-defined threadgroups are reported.
 * COMMENTS
 *  This test functionally equals to nsk/jdb/threadgroups/threadgroups001
 *  test and replaces it.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jdb.threadgroups.threadgroups002.threadgroups002a
 * @run driver
 *      nsk.jdb.threadgroups.threadgroups002.threadgroups002
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -jdb=${test.jdk}/bin/jdb
 *      -java.options="${test.vm.opts} ${test.java.opts}"
 *      -workdir=.
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package nsk.jdb.threadgroups.threadgroups002;

import nsk.share.*;
import nsk.share.jdb.*;

import java.io.*;
import java.util.*;

public class threadgroups002 extends JdbTest {

    public static void main (String argv[]) {
        debuggeeClass =  DEBUGGEE_CLASS;
        firstBreak = FIRST_BREAK;
        lastBreak = LAST_BREAK;
        new threadgroups002().runTest(argv);
    }

    static final String PACKAGE_NAME     = "nsk.jdb.threadgroups.threadgroups002";
    static final String TEST_CLASS       = PACKAGE_NAME + ".threadgroups002";
    static final String DEBUGGEE_CLASS   = TEST_CLASS + "a";
    static final String FIRST_BREAK      = DEBUGGEE_CLASS + ".main";
    static final String LAST_BREAK       = DEBUGGEE_CLASS + ".lastBreak";

    protected void runCases() {
        String[] reply;
        Paragrep grep;
        int count;
        Vector v;
        String found;

        jdb.setBreakpointInMethod(LAST_BREAK);
        jdb.receiveReplyFor(JdbCommand.cont);

        reply = jdb.receiveReplyFor(JdbCommand.threadgroups);
        grep = new Paragrep(reply);
        count = grep.find(threadgroups002a.THREADGROUP_NAME);
        if (count != threadgroups002a.numThreadGroups ) {
            failure("Unexpected number of " + threadgroups002a.THREADGROUP_NAME + " was listed: " + count +
                "\n\texpected value: " + threadgroups002a.numThreadGroups);
        }

        jdb.contToExit(1);
    }
}
