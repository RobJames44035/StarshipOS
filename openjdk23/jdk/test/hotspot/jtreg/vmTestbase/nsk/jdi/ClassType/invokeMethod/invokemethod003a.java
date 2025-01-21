/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ClassType.invokeMethod;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>invokemethod003a</code> is deugee's part of the invokemethod003.
 */
public class invokemethod003a {

    public final static String anotherClassName = "invokemethod003b";
    public final static String class2Check = "invokemethod003Child";
    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 50;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        invokemethod003Child child = new invokemethod003Child();
        invokemethod003b imb = new invokemethod003b();
        pipe.println(invokemethod003.SGNL_READY);

        String instr = pipe.readln(); // brkpLineNumber
        if (instr.equals(invokemethod003.SGNL_QUIT)) {
            log.display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("DEBUGEE> unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }

    public static long publicFromParent(int one, int two, int three) {
        return one + two + three;
    }

    protected static long protectFromParent(int one, int two, int three) {
        return one + two + three;
    }

    private static long privateFromParent(int one, int two, int three) {
        return one + two + three;
    }
}

class invokemethod003Child extends invokemethod003a {
    invokemethod003Child() {
    }

    public static long fromChild(int one, int two, int three) {
        return one + two + three;
    }
}

class invokemethod003b {
    public void run() {
        int i = 1;
    }
}
