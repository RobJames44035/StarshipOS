/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.VMDisconnectEvent._itself_;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


// This class is the debugged application in the test

public class disconnect001a {
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int JCK_STATUS_BASE = 95;
    static final String COMMAND_READY = "ready";
    static final String COMMAND_QUIT = "quit";

    public static void main(String args[]) {
        disconnect001a _disconnect001a = new disconnect001a();
        System.exit(JCK_STATUS_BASE + _disconnect001a.communication(args));
    }

    int communication( String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(COMMAND_READY);
        String command = pipe.readln();
        if (!command.equals(COMMAND_QUIT)) {
              System.err.println("TEST BUG: Debugee: unknown command: " +  command);
              return FAILED;
        }
        return PASSED;
    }
}
