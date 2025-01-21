/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ClassType.invokeMethod;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>invokemethod006a</code> is deugee's part of the invokemethod006.
 */
public class invokemethod006a {

    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 46;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(invokemethod006.SGNL_READY);
        String instr = pipe.readln();
        log.display("breakpoint line"); // brkpLineNumber
        instr = pipe.readln();
        if (instr.equals(invokemethod006.SGNL_QUIT)) {
            log.display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("DEBUGEE> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }

    public static int justMethod(int val) {
        return val;
    }

}
