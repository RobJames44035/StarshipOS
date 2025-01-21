/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.MethodExitEvent.method;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import java.io.*;

//    THIS TEST IS LINE NUMBER SENSITIVE
// This class is the debugged application in the test
public class method001a {

    // exit status constants
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int JCK_STATUS_BASE = 95;

    // synchronization commands
    static final String COMMAND_READY = "ready";
    static final String COMMAND_QUIT  = "quit";
    static final String COMMAND_GO    = "go";
    static final String COMMAND_DONE  = "done";

    // line numbers for auxilary breakpoints
    public static final int STARTING_BREAKPOINT_LINE = 86;
    public static final int ENDING_BREAKPOINT_LINE = 91;

    // scaffold objects
    static private ArgumentHandler argHandler;
    static private Log log;
    static private IOPipe pipe;

    // flags and counters
    static private int flag;
    static private int depth;
    static private boolean methodInvoked;

    // start debuggee
    public static void main(String args[]) {
        method001a _method001a = new method001a();
        System.exit(JCK_STATUS_BASE + _method001a.run(args, System.err));
    }

    // perform the test
    int run(String args[], PrintStream out) {
        argHandler = new ArgumentHandler(args);
        log = new Log(out, argHandler);
        pipe = argHandler.createDebugeeIOPipe();

        depth = 10;
        flag = 0;

        // notify debugger that debuggee has been started
        pipe.println(COMMAND_READY);

        // wait for GO commnad from debugger
        String command = pipe.readln();
        if (!command.equals(COMMAND_GO)) {
            log.complain("TEST BUG: Debugee: unknown command: " + command);
            return FAILED;
        }

        methodInvoked = false; // STARTING_BREAKPOINT_LINE

        // invoke checked method
        foo();

        methodInvoked = true; // ENDING_BREAKPOINT_LINE

        // notify debugger that checked method has been invoked
        pipe.println(COMMAND_DONE);

        // wait for command QUIT from debugger
        command = pipe.readln();
        if (!command.equals(COMMAND_QUIT)) {
            System.err.println("TEST BUG: Debugee: unknown command: " + command);
            return FAILED;
        }

        return PASSED;
    }

    // checked method
    void foo() {
        flag = 1;
        if (depth > 1) {
            depth--;
            foo();
        }
        flag = 3;
    }
}
