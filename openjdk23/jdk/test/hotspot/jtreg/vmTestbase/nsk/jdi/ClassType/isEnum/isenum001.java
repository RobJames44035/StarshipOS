/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.ClassType.isEnum;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;

import java.io.*;
import java.util.*;

/**
 * The debugger application of the test.
 */
public class isenum001 {

    //------------------------------------------------------- immutable common fields

    final static String SIGNAL_READY = "ready";
    final static String SIGNAL_GO    = "go";
    final static String SIGNAL_QUIT  = "quit";

    private static int waitTime;
    private static int exitStatus;
    private static ArgumentHandler     argHandler;
    private static Log                 log;
    private static Debugee             debuggee;
    private static ReferenceType       debuggeeClass;

    //------------------------------------------------------- mutable common fields

    private final static String prefix = "nsk.jdi.ClassType.isEnum.";
    private final static String className = "isenum001";
    private final static String debuggerName = prefix + className;
    private final static String debuggeeName = debuggerName + "a";

    //------------------------------------------------------- test specific fields

    private final static String[] testedFieldNames = {"f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8"};

    //------------------------------------------------------- immutable common methods

    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    private static void display(String msg) {
        log.display("debugger > " + msg);
    }

    private static void complain(String msg) {
        log.complain("debugger FAILURE > " + msg);
    }

    public static int run(String argv[], PrintStream out) {

        exitStatus = Consts.TEST_PASSED;

        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        waitTime = argHandler.getWaitTime() * 60000;

        debuggee = Debugee.prepareDebugee(argHandler, log, debuggeeName);

        debuggeeClass = debuggee.classByName(debuggeeName);
        if ( debuggeeClass == null ) {
            complain("Class '" + debuggeeName + "' not found.");
            exitStatus = Consts.TEST_FAILED;
        }

        execTest();

        debuggee.quit();

        return exitStatus;
    }

    //------------------------------------------------------ mutable common method

    private static void execTest() {
        //debuggee.receiveExpectedSignal(SIGNAL_GO);

        for (int i=0; i < testedFieldNames.length; i++) {
            check(testedFieldNames[i]);
            display("");
        }

        display("Checking completed!");
    }

    //--------------------------------------------------------- test specific methods

    private static void check (String fieldName) {
        try {
            ClassType checkedClass = (ClassType)debuggeeClass.fieldByName(fieldName).type();
            String className = checkedClass.name();
            if (checkedClass.isEnum()) {
                display("CHECK PASSED: ClassType.isEnum() returned expected true for type: " + className);
            } else {
                complain("ClassType.isEnum() returned unexpected false for type: " + className);
                exitStatus = Consts.TEST_FAILED;
            }

        } catch (Exception e) {
            complain("Unexpected exception while checking of " + className + ": " + e);
            e.printStackTrace(System.out);
            exitStatus = Consts.TEST_FAILED;
        }
    }
}
//--------------------------------------------------------- test specific classes
