/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventRequestManager.breakpointRequests;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * This is a debuggee class containing different types of methods.
 */
public class breakpreq001t {
    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args); // breakpreq001.DEBUGGEE_LNS[0]
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(breakpreq001.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(breakpreq001.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(breakpreq001.JCK_STATUS_BASE +
                breakpreq001.FAILED);
        }
        System.exit(breakpreq001.JCK_STATUS_BASE +
            breakpreq001.PASSED);
    }

// dummy methods are below
    byte byteMeth() {
        return -128; // breakpreq001.DEBUGGEE_LNS[1]
    }
    short shortMeth() {
        return 0; // breakpreq001.DEBUGGEE_LNS[2]
    }
    private int prMeth() {
        return 1; // breakpreq001.DEBUGGEE_LNS[3]
    }
    protected long protMeth() {
        return 9223372036854775807L; // breakpreq001.DEBUGGEE_LNS[4]
    }
    float floatMeth() {
        return 0.03456f; // breakpreq001.DEBUGGEE_LNS[5]
    }
    double doubleMeth() {
        return -56.789D; // breakpreq001.DEBUGGEE_LNS[6]
    }
    char charMeth() {
        return '_'; // breakpreq001.DEBUGGEE_LNS[7]
    }
    private boolean boolMeth() {
        return true; // breakpreq001.DEBUGGEE_LNS[8]
    }
    public String pubMeth() {
        return "returning string"; // breakpreq001.DEBUGGEE_LNS[9]
    }
}
