/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>redefineclasses004a</code> is deugee's part of the redefineclasses004.
 * changing field modifiers - adding static
 */

public class redefineclasses004a {

    //preexisting fields
    static public      Object field001;
    static protected   Object field002;
    static private     Object field003;
    static             Object field004;

    static public       Object field005 = null;
    static protected    Object field006 = null;
    static private      Object field007 = null;
    static              Object field008 = null;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instr = pipe.readln();

        if (instr.equals("quit")) {
            log.display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("DEBUGEE> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }
}
