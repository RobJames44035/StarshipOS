/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ReferenceType._bounds_;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>bounds002a</code> is deugee's part of the bounds002.
 */
public class bounds002a {

    public final static String testedFieldName = "testedObj";
    public final static String testedMethod = "justMethod";

    private static bounds002b testedObj = new bounds002b();

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(bounds002.SGNL_READY);
        String instr = pipe.readln();
        if (instr.equals(bounds002.SGNL_QUIT)) {
            log.display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("DEBUGEE> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }
}

class bounds002b {
    private static Object testedObj;
    public static int justMethod(int val) {
        return val;
    }
}
