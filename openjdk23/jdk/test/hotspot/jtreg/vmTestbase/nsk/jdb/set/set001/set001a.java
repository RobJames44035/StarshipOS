/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.set.set001;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class set001a {

    private static final String DEBUGGEE_PASSED = "Debuggee PASSED";
    private static final String DEBUGGEE_FAILED = "Debuggee FAILED";

    static set001a _set001a = new set001a();

    public static void main(String args[]) {
       System.exit(set001.JCK_STATUS_BASE + _set001a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);
        String debuggeeResult = DEBUGGEE_PASSED;

        int localInt = 0;
        lastBreak();
        /* jdb should change values of fileds and variables */

        if (set001a.myStaticField != Integer.MIN_VALUE) {
            errorMessage += "\nWrong value of set001a.myStaticField: " + set001a.myStaticField + ", expected: " + Integer.MIN_VALUE;
        }
        if (_set001a.myInstanceField != Long.MAX_VALUE) {
            errorMessage += "\nWrong value of _set001a.myInstanceField: " + _set001a.myInstanceField + ", expected: " + Long.MAX_VALUE;
        }
        if (errorMessage.length() > 0) {
            debuggeeResult = DEBUGGEE_FAILED;
        }

        lastBreak(); // a breakpoint to check value of debuggeeResult

        log.display(debuggeeResult);
        if (debuggeeResult.equals(DEBUGGEE_PASSED)) {
            return set001.PASSED;
        } else {
            return set001.FAILED;
        }
    }

    static String errorMessage = "";
    static private int myStaticField;
    protected long myInstanceField;

}
