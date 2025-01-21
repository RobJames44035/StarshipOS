/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>redefineclasses027a</code> is deugee's part of the redefineclasses027.
 */
public class redefineclasses027a {

    private static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instr = pipe.readln();
        if (instr.equals("load_class")) {
            // to force ClassPrepareEvent, create instance
            redefineclasses027c obj = new redefineclasses027c();
            redefineclasses027ic.classPrepare = true;
        } else {
            log.complain("DEBUGEE> unexpected signal of debugger.");
            System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
        }

        instr = pipe.readln();
        if (instr.equals("quit")) {
            log.display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

    }
}

abstract class redefineclasses027ic implements redefineclasses027i {
    static boolean classPrepare = false;
}
