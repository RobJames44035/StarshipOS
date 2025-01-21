/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>redefineclasses007a</code> is deugee's part of the redefineclasses007.
 *  changing method modifiers (1 method to static)
 */
public class redefineclasses007a {

    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 49;

    private static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instr = pipe.readln(); // brkpLineNumber
        if (!instr.equals("continue")) {
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

    static public void dummyMethod01() {
    }

    protected void dummyMethod02() {
    }

    private void dummyMethod03() {
    }

    void dummyMethod04() {
    }
}
