/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ReferenceType.sourceDebugExtension;

import com.sun.jdi.ReferenceType;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This is dummy debuggee class
 */
public class srcdebugx001t {
    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(srcdebugx001.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(srcdebugx001.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(srcdebugx001.JCK_STATUS_BASE +
                srcdebugx001.FAILED);
        }
        System.exit(srcdebugx001.JCK_STATUS_BASE +
            srcdebugx001.PASSED);
    }
}
