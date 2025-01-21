/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ExceptionEvent.catchLocation;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import java.io.*;
import java.lang.Integer.*;

// This class is the debugged application in the test

class location002a {
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int JCK_STATUS_BASE = 95;

    // synchronization commands
    static final String COMMAND_READY = "ready";
    static final String COMMAND_QUIT  = "quit";
    static final String COMMAND_GO    = "go";
    static final String COMMAND_DONE  = "done";
    static final String COMMAND_ERROR = "error";

    // start debuggee
    public static void main(String args[]) throws Throwable {
        location002a _location002a = new location002a();
        System.exit(JCK_STATUS_BASE + _location002a.runIt(args, System.err));
    }

    // perform debugee execution
    int runIt(String args[], PrintStream out) throws Throwable {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        Log log = new Log(out, argHandler);

        // notify debuggeer that debuggee started
        pipe.println(COMMAND_READY);

        // wait for command <GO> from debugger
        String command = pipe.readln();
        if (!command.equals(COMMAND_GO)) {
             log.complain("TEST BUG: unknown command: " + command);
             return FAILED;
        }

        // throw uncaught exception, which should terminate debuggee
        System.err.println("Raising NumberFormatException");
        int i = Integer.parseInt("foo");

        // return FAILED if not terminated by uncaught exception
        return FAILED;
    }
}
