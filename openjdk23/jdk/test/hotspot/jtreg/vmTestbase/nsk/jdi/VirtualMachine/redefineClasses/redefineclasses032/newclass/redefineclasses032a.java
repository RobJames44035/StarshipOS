/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>redefineclasses032a</code> is deugee's part of the redefineclasses032.
 */
public class redefineclasses032a {

    public final static String brkpMethodName = "justMethod";
    public final static int brkpLineNumber = 63;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instr;
        do {
            instr = pipe.readln();
            if (instr.equals("perform")) {
                justMethod();
            } else if (instr.equals("quit")) {
                log.display(instr);
                break;
            } else {
                log.complain("DEBUGEE> unexpected signal of debugger.");
                System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
            }
        } while (true);
        log.display("completed succesfully.");
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    static void justMethod() { // brkpLineNumber
//        int local = 0;
//        return;
    }
}
