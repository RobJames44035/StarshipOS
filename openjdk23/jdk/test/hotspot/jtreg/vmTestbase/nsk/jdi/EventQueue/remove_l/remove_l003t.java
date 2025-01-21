/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventQueue.remove_l;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This is a debuggee part of the test.
 */
public class remove_l003t {
    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        Log log = argHandler.createDebugeeLog();
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        log.display("Debuggee: ready");
        pipe.println(remove_l003.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(remove_l003.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd + "\nDebuggee: exiting");
            System.exit(remove_l003.JCK_STATUS_BASE +
                remove_l003.FAILED);
        }
        log.display("Debuggee: exiting");
        System.exit(remove_l003.JCK_STATUS_BASE +
            remove_l003.PASSED);
    }
}
