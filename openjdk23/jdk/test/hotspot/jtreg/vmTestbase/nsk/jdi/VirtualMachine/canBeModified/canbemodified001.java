/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.canBeModified;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import java.io.*;

/**
 * The test checks up <t>com.sun.jdi.VirtualMachine.canBeModified()</t>
 * to return <code>true</code> unconditionally.
 */

public class canbemodified001 {

    private final static String prefix = "nsk.jdi.VirtualMachine.canBeModified.";
    private final static String debuggerName = prefix + "canbemodified001";
    public final static String debugeeName = debuggerName + "a";

    private static int exitStatus;
    private static Log log;
    private static Debugee debugee;
    private static long waitTime;

    private static void display(String msg) {
        log.display(msg);
    }

    private static void complain(String msg) {
        log.complain(msg + "\n");
    }

    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {

        exitStatus = Consts.TEST_PASSED;

        canbemodified001 thisTest = new canbemodified001();

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);

        waitTime = argHandler.getWaitTime() * 60000;

        Binder binder = new Binder(argHandler, log);
        debugee = binder.bindToDebugee(debugeeName);
        debugee.redirectOutput(log);

        try {
            thisTest.execTest();
        } catch (Throwable e) {
            exitStatus = Consts.TEST_FAILED;
            e.printStackTrace();
        } finally {
            debugee.resume();
            debugee.endDebugee();
        }
        display("Test finished. exitStatus = " + exitStatus);

        return exitStatus;
    }

    private void execTest() throws Failure {

        display("\nTEST BEGINS");
        display("===========");

        boolean canBeModified = debugee.VM().canBeModified();
        if (canBeModified) {
            display("target VM can be modified");
            exitStatus = Consts.TEST_PASSED;
        } else {
            complain("target VM can not be modified");
            exitStatus = Consts.TEST_FAILED;
        }

        display("=============");
        display("TEST FINISHES\n");
    }
}
