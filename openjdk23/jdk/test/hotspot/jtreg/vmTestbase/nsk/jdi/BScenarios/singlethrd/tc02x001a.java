/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BScenarios.singlethrd;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>tc02x001a</code> is deugee's part of the tc02x001.
 */
public class tc02x001a {

    public final static String brkpMethodName = "performTest";
    public final static int brkpLineNumber = 68;

    public final static int checkLastLine = 71;
    static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(tc02x001.SGL_READY);

        tc02x001a obj = null;
        String instr;
        do {
            instr = pipe.readln();
            if (instr.equals(tc02x001.SGL_PERFORM)) {
                performTest();
            } else if (instr.equals(tc02x001.SGL_QUIT)) {
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
        log.display("performTest::line 0"); // brkpLineNumber
        log.display("performTest::breakpoint line");
        log.display("performTest::creating tc02x001aClass1 object");
        new tc02x001aClass1(); // checkLastLine
        log.display("performTest::tc02x001aClass1 object is created");
    }
}

class tc02x001aClass1 {
    tc02x001aClass1() {
        tc02x001a.log.display("tc02x001aClass1::constructor is called");
    }
}
