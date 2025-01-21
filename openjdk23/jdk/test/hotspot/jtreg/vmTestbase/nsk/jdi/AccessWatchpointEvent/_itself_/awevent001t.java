/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.AccessWatchpointEvent._itself_;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This is a debuggee class containing several dummy fields.
 */
public class awevent001t {
// the dummy fields are below
    static byte    byteFld = 127;
    static short   shortFld = -32768;
    static int     intFld = 2147483647;
    static long    longFld = 9223372036854775807L;
    static float   floatFld = 345.1F;
    static double  doubleFld = 6.2D;
    static char    charFld = 'a';
    static boolean booleanFld = false;
    static String  strFld = "string field";

    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        Log log = argHandler.createDebugeeLog();

        do {
            log.display("Debuggee: sending the command: "
                + awevent001.COMMAND_READY);
            pipe.println(awevent001.COMMAND_READY);
            String cmd = pipe.readln();
            log.display("Debuggee: received the command: "
                + cmd);

            if (cmd.equals(awevent001.COMMAND_RUN[0])) {
                if (byteFld == 127)
                    log.display("Debuggee: access to the field \"byteFld\""
                        + " is done");
            } else if (cmd.equals(awevent001.COMMAND_RUN[1])) {
                if (shortFld == -32768)
                    log.display("Debuggee: access to the field \"shortFld\""
                        + " is done");
            } else if (cmd.equals(awevent001.COMMAND_RUN[2])) {
                if (intFld == 2147483647)
                    log.display("Debuggee: access to the field \"intFld\""
                        + " is done");
            } else if (cmd.equals(awevent001.COMMAND_RUN[3])) {
                if (longFld == 9223372036854775807L)
                    log.display("Debuggee: access to the field \"longFld\""
                        + " is done");
            } else if (cmd.equals(awevent001.COMMAND_RUN[4])) {
                if (floatFld == 345.1F)
                    log.display("Debuggee: access to the field \"floatFld\""
                        + " is done");
            } else if (cmd.equals(awevent001.COMMAND_RUN[5])) {
                if (doubleFld == 6.2D)
                    log.display("Debuggee: access to the field \"doubleFld\""
                        + " is done");
            } else if (cmd.equals(awevent001.COMMAND_RUN[6])) {
                if (charFld == 'a')
                    log.display("Debuggee: access to the field \"charFld\""
                        + " is done");
            } else if (cmd.equals(awevent001.COMMAND_RUN[7])) {
                if (booleanFld == false)
                    log.display("Debuggee: access to the field \"booleanFld\""
                        + " is done");
            } else if (cmd.equals(awevent001.COMMAND_RUN[8])) {
                if (strFld.equals("string field"))
                    log.display("Debuggee: access to the field \"strFld\""
                        + " is done");
            } else if (cmd.equals(awevent001.COMMAND_BREAKPOINT)) {
                breakpoint();
            } else if (cmd.equals(awevent001.COMMAND_QUIT)) {
                break;
            } else {
                System.err.println("TEST BUG: unknown debugger command: "
                    + cmd);
                System.exit(awevent001.JCK_STATUS_BASE +
                    awevent001.FAILED);
            }
        } while(true);
        log.display("Debuggee: exiting");
        System.exit(awevent001.JCK_STATUS_BASE +
            awevent001.PASSED);
    }

    private static void breakpoint() {
        // empty method used for setting a breakpoint in
    }
}
