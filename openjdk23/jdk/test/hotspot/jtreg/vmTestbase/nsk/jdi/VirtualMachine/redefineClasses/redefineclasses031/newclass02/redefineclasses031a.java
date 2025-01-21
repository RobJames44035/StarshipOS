/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>redefineclasses031a</code> is deugee's part of the redefineclasses031.
 */
public class redefineclasses031a {

    public final static String brkpMethodName = "performTest";
    public final static int brkpLineNumber = 78;

    static final int [] NEW_VALUES = {
        1, 2, 3, 4, 5
    };

    static int testedField = 0;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instr;
        do {
            instr = pipe.readln();
            if (instr.equals("perform")) {
                performTest();
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

    static public void doSomething() {

        testedField = NEW_VALUES[1];
//                                 ^^^ will be changed
    }

    static void performTest() {
        // invoking of redefined method
        for(int i = 0; i < NEW_VALUES.length; i++) {
            // brkpLineNumber
            doSomething();
        }
    }
}
