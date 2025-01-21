/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.ReferenceType.visibleFields;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;

import java.io.*;
import java.util.*;

/**
 * The debugger application of the test.
 */
public class visibfield006 {

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

    private final static String prefix = "nsk.jdi.ReferenceType.visibleFields.";
    private final static String className = "visibfield006";
    private final static String debuggerName = prefix + className;
    private final static String debuggeeName = debuggerName + "a";

    //------------------------------------------------------- test specific fields

    private final static String[] expectedFieldNames = {"f1", "f2", "f3"};
    private final static String[] expectedEnumFieldsNames = { "e1", "e2" };

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
            check(expectedFieldNames[i]);
            display("");
        }
        display("Checking completed!");
    }

    //--------------------------------------------------------- test specific methods

    private static void check (String fieldName) {
        try {
            ClassType checkedClass = (ClassType)debuggeeClass.fieldByName(fieldName).type();
            String className = checkedClass.name();

            // check there are enum constant fields
            List<Field> l = checkedClass.visibleFields();
            if (l.isEmpty()) {
                complain("\t ReferenceType.visibleFields() returned empty list for type: " + className);
                exitStatus = Consts.TEST_FAILED;
            } else {
                for (int i = 0; i < expectedEnumFieldsNames.length; i++) {
                    boolean hasField = false;

                    Iterator<Field> it = l.iterator();
                    while (it.hasNext()) {
                        Field checkedField = it.next();
                        if (expectedEnumFieldsNames[i].equals(checkedField.name()) &&
                            checkedField.type().equals(checkedClass) )

                            hasField = true;
                    }
                    if (hasField) {
                        display("enum " + className + " has field " + expectedEnumFieldsNames[i]);
                        display("\t of type " + className);
                    } else {
                        complain("enum " + className + " does not have field " + expectedEnumFieldsNames[i]);
                        complain("\t of type " + className);
                        exitStatus = Consts.TEST_FAILED;
                    }
                }
                // check that ambiguous field in not returned
                Iterator<Field> it = l.iterator();
                while (it.hasNext()) {
                    Field field = it.next();
                    if (field.name().equals("i1")) {
                        complain("enum " + className + " has ambigous field i1 ");
                        exitStatus = Consts.TEST_FAILED;
                    }
                }
            }
        } catch (Exception e) {
            complain("Unexpected exception while checking of " + className + ": " + e);
            e.printStackTrace(System.out);
            exitStatus = Consts.TEST_FAILED;
        }
    }
}
//--------------------------------------------------------- test specific classes
