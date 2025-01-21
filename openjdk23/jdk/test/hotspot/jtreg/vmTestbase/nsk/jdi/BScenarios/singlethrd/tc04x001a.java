/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BScenarios.singlethrd;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>tc04x001a</code> is deugee's part of the tc02x001.
 */
public class tc04x001a {

    public final static int checkExBrkpLine = 69;
    static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(tc04x001.SGL_READY);

        tc04x001a obj = null;
        String instr;
        do {
            instr = pipe.readln();
            log.display("instruction \"" + instr +"\"");
            if (instr.equals(tc04x001.SGL_LOAD)) {
                tc04x001aException.loadThis = true;
                performTest();
            } else if (instr.equals(tc04x001.SGL_QUIT)) {
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

    public static void performTest() {
        log.display("performTest:: throwing tc04x001aException");
        try {
            throw new tc04x001aException(); // checkExBrkpLine
        } catch (tc04x001aException e) {
        }
    }
}

class tc04x001aException extends RuntimeException {
    static boolean loadThis = false;
}
