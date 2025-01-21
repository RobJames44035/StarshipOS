/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BScenarios.singlethrd;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>tc03x003a</code> is deugee's part of the tc02x001.
 */
public class tc03x003a {

    public final static int checkLastLine1 = 76;
    public final static int checkLastLine2 = 78;
    static Log log;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(tc03x003.SGL_READY);

        tc03x003a obj = null;
        String instr;
        do {
            instr = pipe.readln();
            log.display("instruction \"" + instr +"\"");
            if (instr.equals(tc03x003.SGL_LOAD)) {
                tc03x003aClass1.loadThis = true;
                performTest();
            } else if (instr.equals(tc03x003.SGL_QUIT)) {
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
        log.display("performTest::creating tc03x003aClass1 object");
        new tc03x003aClass1();
        log.display("performTest::tc03x003aClass1 object is created");
    }
}

class tc03x003aClass1 {

    static boolean loadThis = false; // checkLastLine1

    tc03x003aClass1() { // checkLastLine2
        tc03x003a.log.display("tc03x003aClass1::constructor is called");
    }
}
