/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package nsk.jdi.EventQueue.remove_l;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>remove_l005a</code> is deugee's part of the remove_l005.
 */
public class remove_l005a {
    private static Log log;
    private static IOPipe pipe;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(remove_l005.SGNL_READY);
        int status = run();
        System.exit(status + Consts.JCK_STATUS_BASE);
    }

    public static int run() {
        for (int i = 0; ; i++) {
            String instr = pipe.readln();
            if (instr.equals(remove_l005.SGNL_GO)) {
                log.display("Go for iteration #" + i);
            } else if (instr.equals(remove_l005.SGNL_QUIT)) {
                log.display("Quit iterations");
                return Consts.TEST_PASSED;
            } else {
                log.complain("Unexpected signal received: " + instr);
                return Consts.TEST_FAILED;
            }

            // next line is for breakpoint
            log.display("Breakpoint line reached"); // remove_l005.brkpLineNumber
        }
    }
}
