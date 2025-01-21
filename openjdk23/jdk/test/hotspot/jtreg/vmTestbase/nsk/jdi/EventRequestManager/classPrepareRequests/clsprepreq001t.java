/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventRequestManager.classPrepareRequests;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This is a debuggee class
 */
public class clsprepreq001t {
    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(clsprepreq001.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(clsprepreq001.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(clsprepreq001.JCK_STATUS_BASE +
                clsprepreq001.FAILED);
        }
        System.exit(clsprepreq001.JCK_STATUS_BASE +
            clsprepreq001.PASSED);
    }
}
