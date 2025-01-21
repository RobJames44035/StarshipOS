/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.EventRequest._bounds_;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>eventrequest001a</code> is deugee's part of the eventrequest001.
 */
public class eventrequest001a {

    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 47;
    private static Log log;
    private static IOPipe pipe;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(eventrequest001.SGNL_READY);
        String instr = pipe.readln(); // brkpLineNumber
        if (instr.equals(eventrequest001.SGNL_QUIT)) {
            log.display("constructor> completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("constructor> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }
}
