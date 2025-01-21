/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ClassType.invokeMethod;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>invokemethod004a</code> is deugee's part of the invokemethod004.
 */
public class invokemethod004a {

    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 48;
    public final static String testException = "java.lang.NullPointerException";


    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(invokemethod004.SGNL_READY);
        String instr = pipe.readln();
        log.display("breakpoint line"); // brkpLineNumber
        instr = pipe.readln();
        if (instr.equals(invokemethod004.SGNL_QUIT)) {
            log.display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("DEBUGEE> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }

    public static void throwNPE() {
        Object obj = null;
        String tmp = obj.toString();
    }

    protected static void throwCaughtNPE() {
        Object obj = null;
        try {
            String tmp = obj.toString();
        } catch (NullPointerException e) {
        }
    }
}
