/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */


package nsk.jdi.ClassUnloadRequest.addClassExclusionFilter;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>exclfilter002a</code> is deugee's part of the test.
 */
public class exclfilter002a {

    static public Log log = null;
    static public IOPipe pipe = null;

    public static void main (String argv[]) {

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instruction = pipe.readln();
        if (instruction.equals("quit")) {
            log.display("DEBUGEE> completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("DEBUGEE> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }

}
