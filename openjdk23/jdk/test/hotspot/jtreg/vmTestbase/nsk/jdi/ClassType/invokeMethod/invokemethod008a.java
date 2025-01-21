/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ClassType.invokeMethod;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>invokemethod008a</code> is deugee's part of the invokemethod008.
 */
public class invokemethod008a {

    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 51;
    private static Log log;
    private static IOPipe pipe;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(invokemethod008.SGNL_READY);

        String instr = pipe.readln();
        // the below line is a breakpoint.
        // Debugger will invoke justMethod().
        instr = pipe.readln(); // brkpLineNumber
        if (instr.equals(invokemethod008.SGNL_QUIT)) {
            log.display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }

    public static int justMethod() {
        try {
            pipe.println(invokemethod008.SGNL_READY);
            log.display("invoked_method:: waiting response from debugger...");
            String instr = pipe.readln();
            if (instr.equals(invokemethod008.SGNL_FINISH)) {
                log.display("invoked_method:: completed succesfully.");
                return Consts.TEST_PASSED;
            } else if (instr.equals(invokemethod008.SGNL_ABORT)) {
                log.display("invoked_method:: aborted.");
            }
        } catch(Exception e) {
            log.display("unexpected exception " + e);
        }
        return Consts.TEST_FAILED;
    }

}
