/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.VMStartEvent.thread;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


// This class is the debugged application in the test

class thread001a {
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int JCK_STATUS_BASE = 95;
    static final String COMMAND_READY = "ready";
    static final String COMMAND_QUIT = "quit";

    public static void main(String args[]) {
        thread001a _thread001a = new thread001a();
        System.exit(JCK_STATUS_BASE + _thread001a.communication(args));
    }

    int communication( String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        // notify debbugger that debuggee started
        pipe.println(COMMAND_READY);

        // wait from debugger command QUIT
        String command = pipe.readln();
        if (!command.equals(COMMAND_QUIT)) {
              System.err.println("TEST BUG: Debugee: unknown command: " +
                   command);
              return FAILED;
        }
        return PASSED;
    }
}
