/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BreakpointRequest._bounds_;

import nsk.share.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import com.sun.jdi.request.*;

import java.io.*;
import java.util.*;

/**
 * The test checks up the            <br>
 *  - <code>addThreadFilter</code>,  <br>
 *  - <code>addInstanceFilter</code> <br>
 * methods with <code>null</code> argument value.
 * In any cases <code>NullPointerException</code> is expected.
 */
public class filters001 {

    private final static String prefix = "nsk.jdi.BreakpointRequest._bounds_.";
    private final static String debuggerName = prefix + "filters001";
    private final static String debugeeName = debuggerName + "a";

    private static int exitStatus;
    private static Log log;
    private static Debugee debugee;
    private static String classToCheck = prefix + filters001a.classToCheck;
    private static String indent = "                  : ";

    private static void display(String msg) {
        log.display("debugger> " + msg);
    }

    private static void complain(String msg) {
        log.complain("debugger FAILURE> " + msg + "\n");
    }

    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {

        exitStatus = Consts.TEST_PASSED;

        filters001 thisTest = new filters001();

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);

        debugee = Debugee.prepareDebugee(argHandler, log, debugeeName);

        thisTest.execTest();
        display("execTest finished. exitStatus = " + exitStatus);

        return exitStatus;
    }

    private void execTest() {

        ReferenceType debugeeRef = debugee.classByName(debugeeName);
        ReferenceType checkedClsRef = debugee.classByName(classToCheck);

        display("");
        display(">>>" + filters001a.objName);
        display("----------------------");
        Field field = debugeeRef.fieldByName(filters001a.objName);
        Value val = debugeeRef.getValue(field);

        BreakpointRequest request = debugee.setBreakpoint(checkedClsRef,
                                                    filters001a.brkptMethodName,
                                                    filters001a.brkptLineNumber);

        display("");
        addThreadFilter(request, (ThreadReference )val);
        display("");
        addInstanceFilter(request, (ObjectReference )val);

        display("");
        debugee.quit();
    }

    private void addThreadFilter(BreakpointRequest request, ThreadReference thread) {

        display("addThreadFilter   :ThreadReference> null");
        try {
            request.addThreadFilter(thread);
            complain("*****NullPointerException is not thrown");
            exitStatus = Consts.TEST_FAILED;
        } catch (NullPointerException e) {
            display("!!!Expected " + e);
        } catch (Exception e) {
            complain("*****Unexpected " + e);
            exitStatus = Consts.TEST_FAILED;
        }
    }

    private void addInstanceFilter(BreakpointRequest request,
                                                ObjectReference instance) {

        display("addInstanceFilter :ObjectReference> null");

        try {
            request.addInstanceFilter(instance);
            complain("*****NullPointerException is not thrown");
            exitStatus = Consts.TEST_FAILED;
        } catch (NullPointerException e) {
            display("!!!Expected " + e);
        } catch (Exception e) {
            complain("*****Unexpected " + e);
            exitStatus = Consts.TEST_FAILED;
        }
    }
}
