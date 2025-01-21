/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.exit;

import nsk.share.Log;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the exit002 JDI test.
 */

public class exit002a {

    static Log log;

    private static void log1(String message) {
        log.display("**> exit002a: " + message);
    }

    public static void main(String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);

        log = argHandler.createDebugeeLog();
        log1("debuggee started!");

        // informing a debugger of readyness
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        pipe.println("ready");

        /*
         * In this test debugger kills debuggee using VirtualMachine.exit, so
         * standard JDI tests communication protocol isn't used here
         */
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (Throwable t) {
            // ignore all exceptions
        }
    }
}
