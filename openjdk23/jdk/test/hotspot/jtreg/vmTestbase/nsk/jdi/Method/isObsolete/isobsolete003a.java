/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package nsk.jdi.Method.isObsolete;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE
/**
 * The debugged application of the test.
 */
public class isobsolete003a {

    private static int exitStatus;
    private static ArgumentHandler argHandler;
    private static Log log;
    private static IOPipe pipe;

    public static void main (String argv[]) {

        exitStatus = Consts.TEST_FAILED;
        argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);

        isobsolete003b.foo(1);
        isobsolete003b.foo(2); // isobsolete003.brkpMainLineNumber

        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    static void display(String msg) {
        log.display("debuggee > " + msg);
    }

    static void complain(String msg) {
        log.complain("debuggee FAILURE > " + msg);
    }
}
