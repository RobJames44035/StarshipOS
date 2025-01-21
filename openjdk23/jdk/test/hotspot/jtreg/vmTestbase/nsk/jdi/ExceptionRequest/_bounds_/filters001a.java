/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


package nsk.jdi.ExceptionRequest._bounds_;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>filters001a</code> is deugee's part of the test.
 */
public class filters001a {

    public static void main (String argv[]) {

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);

        pipe.println("ready");

        String instr = pipe.readln();

        if (instr.equals("quit")) {
            log.display("DEBUGEE> completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("DEBUGEE> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }
}
