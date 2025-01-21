/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventRequestManager.modificationWatchpointRequests;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This is a debuggee class containing different kinds of fields
 */
public class modwtchpreq001t {
// instance fields
    byte    byteFld = 127;
    short   shortFld = -32768;
    int     intFld = 2147483647;
    long    longFld = 9223372036854775807L;
    float   floatFld = 5.1F;
    double  doubleFld = 6.2D;
    char    charFld = 'a';
    boolean booleanFld = false;
    String  strFld = "instance field";
// fields with different access rights
    static    short sFld = 32767;
    private   byte prFld = -128;
    public    float pubFld = 0.9F;
    protected double protFld = -435.789D;
    transient int  tFld = -2147483648;
    volatile  long vFld = -922337203685477580L;
    final     char fFld = 'z';

    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(modwtchpreq001.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(modwtchpreq001.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(modwtchpreq001.JCK_STATUS_BASE +
                modwtchpreq001.FAILED);
        }
        System.exit(modwtchpreq001.JCK_STATUS_BASE +
            modwtchpreq001.PASSED);
    }
}
