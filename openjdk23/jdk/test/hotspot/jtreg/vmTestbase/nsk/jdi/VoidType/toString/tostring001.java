/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VoidType.toString;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import com.sun.jdi.request.*;
import com.sun.jdi.event.*;
import com.sun.jdi.connect.*;
import java.io.*;
import java.util.*;

/**
 * The debugger application of the test.
 */
public class tostring001 {

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

    private final static String prefix = "nsk.jdi.VoidType.toString.";
    private final static String className = "tostring001";
    private final static String debuggerName = prefix + className;
    private final static String debuggeeName = debuggerName + "a";

    //------------------------------------------------------- test specific fields

    /** debuggee's methods for check **/
    private final static String checkedMethods[] = {
        "Mv" ,  "MvS",  "MvI",  "MvY",  "MvU",  "MvN",
        "MvR",  "MvP",  "MvSM", "MvIM", "MvYM", "MvPM", "MvNP"
                                                   };

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

        BreakpointRequest brkp = debuggee.setBreakpoint(debuggeeClass,
                                                    tostring001a.brkpMethodName,
                                                    tostring001a.brkpLineNumber);
        debuggee.resume();

        debuggee.sendSignal(SIGNAL_GO);
        Event event = null;

        // waiting the breakpoint event
        try {
            event = debuggee.waitingEvent(brkp, waitTime);
        } catch (InterruptedException e) {
            throw new Failure("unexpected InterruptedException while waiting for Breakpoint event");
        }
        if (!(event instanceof BreakpointEvent)) {
            debuggee.resume();
            throw new Failure("BreakpointEvent didn't arrive");
        }

        ThreadReference thread = ((BreakpointEvent)event).thread();
        List<Value> params = new Vector<Value>();
        ClassType testedClass = (ClassType)debuggeeClass;
        ObjectReference testedObject = null;

        // Finding of debuggee's class constructor
        Method ctor = debuggee.methodByName(debuggeeClass, "<init>");

        try {
            testedObject = testedClass.newInstance(thread, ctor, params, 0);
        } catch (Exception e) {
            throw new Failure("unexpected " + e + " when invoking debuggee's constructor");
        }

        display("Checking toString() method for debuggee's void type methods...");

        // Check all methods from debuggee
        for (int i = 0; i < checkedMethods.length-1; i++) {

            VoidType voidType = null;
            Method method;

            method = debuggee.methodByName(debuggeeClass, checkedMethods[i]);
            try {
                voidType = (VoidType)method.returnType();
                String str = voidType.toString();
                if (str == null) {
                    complain("toString() returns null for VoidType of debugges's method: " + checkedMethods[i]);
                    exitStatus = Consts.TEST_FAILED;
                } else if (str.length() == 0) {
                    complain("toString() returns empty string for VoidType of debugges's method: " + checkedMethods[i]);
                    exitStatus = Consts.TEST_FAILED;
                } else {
                    display("toString() returns for debuggee's void method " + checkedMethods[i] + " : " + str);
                }
            } catch(Exception e) {
                complain("Unexpected " + e + " when getting VoidType of debuggee's method: " + checkedMethods[i]);
                exitStatus = Consts.TEST_FAILED;
            }

        }

        display("Checking of debuggee's void type methods completed!");
        debuggee.resume();
    }

    //--------------------------------------------------------- test specific methods

}
//--------------------------------------------------------- test specific classes
