/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BScenarios.hotswap;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>tc05x001a</code> is deugee's part of the tc05x001.
 */
public class tc05x001a {

    public final static String brkpMethodName = "method_A";
    public final static int brkpLineNumber = 69;
    private static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instr;
        do {
            instr = pipe.readln();
            if (instr.equals("perform")) {
                runIt();
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

    private static void runIt() {
        tc05x001a obj = new tc05x001a();
        obj.method_A();
    }

    public void method_A() { // brkpLineNumber
        method_B();
        log.display("new line");
//      ^^^^^^^^^^^^^^^^^^^^^^^^ inserted line
    }

    public void method_B() {
        method_C();
    }

    public void method_C() {
        log.display("method_C:: line 1");
        log.display("method_C:: line 2");
        log.display("method_C:: line 3");
        log.display("method_C:: line 4");
    }
}
