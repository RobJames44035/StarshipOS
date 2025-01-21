/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>redefineclasses002a</code> is deugee's part of the redefineclasses002.
 */
public class redefineclasses002a {

    public final static String testedVarName = "testedVar";

    public final static String brkpMethodName = "redefinedMethod";
    public final static int brkpLineNumber = 77;

    public final static int INITIAL_VALUE = 0;
    public final static int REDEFINED_VALUE = 1;

    private static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instr = pipe.readln();
        if (instr.equals("continue")) {
            runIt();
        } else {
            log.complain("DEBUGEE> unexpected signal of debugger.");
            System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
        }

        instr = pipe.readln();
        if (instr.equals("quit")) {
            log.display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("DEBUGEE> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }

    private static void runIt() {
        redefineclasses002a obj = new redefineclasses002a();
        obj.redefinedMethod();
    }

    public void redefinedMethod() {
        int testedVar = INITIAL_VALUE; // brkpLineNumber
        log.display("redefinedMethod> breakpoint line");
        testedVar = REDEFINED_VALUE;
        log.display("redefinedMethod> breakpoint line");
    }
}
