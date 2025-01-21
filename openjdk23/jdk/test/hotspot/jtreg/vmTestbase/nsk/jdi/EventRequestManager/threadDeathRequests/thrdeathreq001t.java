/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventRequestManager.threadDeathRequests;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This is a debuggee class.
 */
public class thrdeathreq001t {
    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(thrdeathreq001.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(thrdeathreq001.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(thrdeathreq001.JCK_STATUS_BASE +
                thrdeathreq001.FAILED);
        }
        System.exit(thrdeathreq001.JCK_STATUS_BASE +
            thrdeathreq001.PASSED);
    }
}
