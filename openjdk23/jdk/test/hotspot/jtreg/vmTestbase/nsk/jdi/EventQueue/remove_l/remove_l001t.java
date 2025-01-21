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
public class remove_l001t {
    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        Log log = argHandler.createDebugeeLog();
// dummy IOPipe: just to avoid:
// "Pipe server socket listening error: java.net.SocketException"
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        log.display("Debuggee: exiting");
        System.exit(remove_l001.JCK_STATUS_BASE +
            remove_l001.PASSED);
    }
}
