/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */


/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jdb/options/connect/connect002.
 * VM Testbase keywords: [quick, jpda, jdb]
 * VM Testbase readme:
 * DESCRIPTION
 *  This is a test for '-connect' option with 'com.sun.jdi.SocketAttach"
 *  connector.
 *  Jdb is started with particular connector argument.
 *  The 'run' command is issued in the superclass of test driver class.
 *  The test is passed if "run nsk.jdb.options.connect.connect002a" string
 *  is found in jdb's output stream:
 *  The test consists of two parts:
 *   connect002.java  - test driver, i.e. launches jdb and debuggee,
 *                  writes commands to jdb, reads the jdb output,
 *   connect002a.java - the debugged application.
 * COMMENTS
 *  The test is similar to nsk/jdb/run/run002, but uses particular connector
 *  overriding settings in ini-file.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jdb.options.connect.connect002.connect002a
 * @run driver
 *      nsk.jdb.options.connect.connect002.connect002
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -connector=attaching
 *      -transport=socket
 *      -jdb=${test.jdk}/bin/jdb
 *      -java.options="${test.vm.opts} ${test.java.opts}"
 *      -workdir=.
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package nsk.jdb.options.connect.connect002;

import nsk.share.*;
import nsk.share.jdb.*;

import java.io.*;
import java.util.*;

public class connect002 extends JdbTest {

    public static void main (String argv[]) {
        debuggeeClass =  DEBUGGEE_CLASS;
        firstBreak = FIRST_BREAK;
        lastBreak = LAST_BREAK;
        new connect002().runTest(argv);
    }

    static final String PACKAGE_NAME = "nsk.jdb.options.connect.connect002";
    static final String TEST_CLASS = PACKAGE_NAME + ".connect002";
    static final String DEBUGGEE_CLASS = TEST_CLASS + "a";
    static final String FIRST_BREAK        = DEBUGGEE_CLASS + ".main";
    static final String LAST_BREAK         = DEBUGGEE_CLASS + ".lastBreak";

    protected boolean shouldPass() {
        String feature = "com.sun.jdi.SocketAttach";
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
