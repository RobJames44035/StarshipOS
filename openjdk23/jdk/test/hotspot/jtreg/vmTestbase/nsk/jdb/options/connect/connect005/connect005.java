/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jdb/options/connect/connect005.
 * VM Testbase keywords: [quick, jpda, jdb]
 * VM Testbase readme:
 * DESCRIPTION
 *  This is a test for '-connect' option with 'com.sun.jdi.SharedMemoryListen"
 *  connector.
 *  Jdb is started with particular connector argument.
 *  The 'run' command is issued in the superclass of test driver class.
 *  The test is passed if "run nsk.jdb.options.connect.connect005a" string
 *  is found in jdb's output stream:
 *  The test consists of two parts:
 *   connect005.java  - test driver, i.e. launches jdb and debuggee,
 *                  writes commands to jdb, reads the jdb output,
 *   connect005a.java - the debugged application.
 * COMMENTS
 *  The test is similar to nsk/jdb/run/run002, but uses particular connector
 *  overriding settings in ini-file.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jdb.options.connect.connect005.connect005a
 * @run driver
 *      nsk.jdb.options.connect.connect005.connect005
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -connector=listening
 *      -transport=shmem
 *      -jdb=${test.jdk}/bin/jdb
 *      -java.options="${test.vm.opts} ${test.java.opts}"
 *      -workdir=.
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package nsk.jdb.options.connect.connect005;

import nsk.share.*;
import nsk.share.jdb.*;

import java.io.*;
import java.util.*;

public class connect005 extends JdbTest {

    public static void main (String argv[]) {    debuggeeClass =  DEBUGGEE_CLASS;
        firstBreak = FIRST_BREAK;
        lastBreak = LAST_BREAK;
        new connect005().runTest(argv);
    }

    static final String PACKAGE_NAME = "nsk.jdb.options.connect.connect005";
    static final String TEST_CLASS = PACKAGE_NAME + ".connect005";
    static final String DEBUGGEE_CLASS = TEST_CLASS + "a";
    static final String FIRST_BREAK        = DEBUGGEE_CLASS + ".main";
    static final String LAST_BREAK         = DEBUGGEE_CLASS + ".lastBreak";

    protected boolean shouldPass() {
        String feature = "com.sun.jdi.SharedMemoryListen";
        if (argumentHandler.shouldPass(feature)) {
            log.println("Test passes because of not implemented feature: " + feature);
            return true;
        }
        return super.shouldPass();
    }

    protected void runCases() {
        String[] reply;
        Paragrep grep;
        int count;
        Vector v;
        String found;

        jdb.contToExit(1);

        if (argumentHandler.isLaunchingConnector()) {
            reply = jdb.getTotalReply();
            grep = new Paragrep(reply);
            v = new Vector();
            v.add(JdbCommand.run);
            v.add(DEBUGGEE_CLASS);
            if (grep.find(v) != 1) {
                failure("jdb failed to run debugged application.");
            }
        }
    }
}
