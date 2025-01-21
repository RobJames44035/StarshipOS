/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ReferenceType.sourceDebugExtension;

import com.sun.jdi.ReferenceType;
import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This debuggee class gets the Class object associated with the class
 * srcdebugx002x containing the SourceDebugExtension attribute.
 */
public class srcdebugx002t {
    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        Class srcdebX;

// get auxiliary class containing the SourceDebugExtension attribute
        try {
            srcdebX = Class.forName(srcdebugx002.SRCDEBUGX_CLASS);
        } catch(Exception e) {
            System.err.println("TEST BUG: caught in debuggee: "
                + e);
            System.exit(srcdebugx002.JCK_STATUS_BASE +
                srcdebugx002.FAILED);
        }

        pipe.println(srcdebugx002.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(srcdebugx002.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(srcdebugx002.JCK_STATUS_BASE +
                srcdebugx002.FAILED);
        }
        System.exit(srcdebugx002.JCK_STATUS_BASE +
            srcdebugx002.PASSED);
    }
}
