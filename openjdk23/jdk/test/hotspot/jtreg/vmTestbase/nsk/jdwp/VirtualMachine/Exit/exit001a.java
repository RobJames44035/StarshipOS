/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.VirtualMachine.Exit;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

public class exit001a {

    public static void main(String args[]) {
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        Log log = argumentHandler.createDebugeeLog();
        log.display("Creating pipe");
        IOPipe pipe = argumentHandler.createDebugeeIOPipe(log);
        log.display("Sending command: " + "ready");
        pipe.println("ready");
        /*
         * In this test debugger kills debuggee using VirtualMachine.exit, so
         * standard JDWP tests communication protocol isn't used here
         */
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (Throwable t) {
            // ignore all exceptions
        }
    }
}
