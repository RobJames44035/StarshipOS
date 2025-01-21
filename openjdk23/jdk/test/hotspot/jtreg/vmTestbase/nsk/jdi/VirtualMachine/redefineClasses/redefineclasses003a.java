/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>redefineclasses003a</code> is deugee's part of the redefineclasses003.
 */
public class redefineclasses003a {
    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 63;

    public final static int INITIAL_VALUE = 0;
    public final static int REDEFINED_VALUE = 1;
    public final static int ASSIGNED_VALUE = 2;
    public final static int REASSIGNED_VALUE = 3;
    public final static String[] testedFields = {
        "Field_1",
        "Field_2",
        "Field_3"
    };

    // tested fields
    private static int    Field_1;
    protected static int  Field_2;
    public static int     Field_3;

    private static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instr = pipe.readln();
        if (instr.equals("continue")) { // brkpLineNumber
            Field_1 = REASSIGNED_VALUE;
            Field_2 = REASSIGNED_VALUE;
            Field_3 = REASSIGNED_VALUE;
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

    public static String value2String(int val) {
        switch (val) {
            case INITIAL_VALUE:
                return "INITIAL_VALUE";
            case REDEFINED_VALUE:
                return "REDEFINED_VALUE";
            case ASSIGNED_VALUE:
                return "ASSIGNED_VALUE";
            case REASSIGNED_VALUE:
                return "REASSIGNED_VALUE";
        }
        return "";
    }

    static {
        Field_1 = INITIAL_VALUE;
        Field_2 = INITIAL_VALUE;
        Field_3 = INITIAL_VALUE;
    }

}
