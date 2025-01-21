/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.Field.typeName;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;

import java.io.*;
import java.util.*;

/**
 * The debugger application of the test.
 */
public class typename002 {

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

    private final static String prefix = "nsk.jdi.Field.typeName.";
    private final static String className = "typename002";
    private final static String debuggerName = prefix + className;
    private final static String debuggeeName = debuggerName + "a";

    //------------------------------------------------------- test specific fields

    private final static String[] expectedFieldNames = {"f1", "f2"};
    private final static String[] expectedTypeNames = { "nsk.jdi.Field.typeName.typename002Enum1",
                                                        "nsk.jdi.Field.typeName.typename002Enum2" };

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
        for (int i=0; i < expectedFieldNames.length; i++) {
            check(expectedFieldNames[i], expectedTypeNames[i]);
            display("");
        }
        display("Checking completed!");
    }

    //--------------------------------------------------------- test specific methods

    private static void check (String fieldName, String typeName) {
        try {
            Field foundField = debuggeeClass.fieldByName(fieldName);
            if (foundField != null) {
                if (foundField.typeName().equals(typeName)) {
                    display("Field " + fieldName + " is of expected type " + typeName);
                } else {
                    complain("Field " + fieldName + " is of unexpected type " + foundField.typeName());
                    exitStatus = Consts.TEST_FAILED;
                }
            } else {
                complain(" Cannot find field " + fieldName);
                exitStatus = Consts.TEST_FAILED;
            }
        } catch (Exception e) {
            complain("Unexpected exception while checking of field " + fieldName + ": " + e);
            e.printStackTrace(System.out);
            exitStatus = Consts.TEST_FAILED;
        }
    }
}
//--------------------------------------------------------- test specific classes
