/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ClassUnloadRequest.addClassExclusionFilter;

import nsk.share.*;
import nsk.share.jdi.*;

import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import java.io.*;

/**
 * Debugger checks up assertion: <br>
 *   <code>InvalidRequestStateException</code> - if this request is currently
 *   enabled or has been deleted. <br>
 * Debugger performs the following steps:<br>
 *  - creates a <code>ClassUnloadRequests</code> and enables it;<br>
 *  - invokes the method <code>addClassExclusionFilter()</code>.<br>
 *  It is expected, <code>InvalidRequestStateException</code> will be thrown;<br>
 *  - deletes this request and invokes the method <code>addClassExclusionFilter()</code><br>
 *  Once again it is expected, <code>InvalidRequestStateException</code> will be thrown;<br>
 */
public class exclfilter002 {

    final static String prefix = "nsk.jdi.ClassUnloadRequest.addClassExclusionFilter.";
    private final static String className = "exclfilter002";
    private final static String debuggerName = prefix + className;
    private final static String debugeeName = debuggerName + "a";

    private static int exitStatus;
    private static Log log;
    private static Debugee debugee;

    private static String pattern = prefix + "Sub*";

    private static void display(String msg) {
        log.display("debugger> " + msg);
    }

    private static void complain(String msg) {
        log.complain("debugger> " + msg + "\n");
    }

    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {

        exclfilter002 tstObj = new exclfilter002();

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);

        debugee = Debugee.prepareDebugee(argHandler, log, debugeeName);
        tstObj.execTest();

        display("execTest finished. exitStatus = " + exitStatus);

        return exitStatus;
    }

    private void execTest() {

        exitStatus = Consts.TEST_PASSED;

        EventRequestManager evm = debugee.getEventRequestManager();

        display(">>>creating ClassUnloadRequest");
        ClassUnloadRequest request = evm.createClassUnloadRequest();

        display("enabled request--------------------");
        display(">>>enabling of the created request");
        request.enable();
        addExclusionFilter(request);

        display("deleted request--------------------");
        display(">>>disabling of the created request");
        request.disable();
        display(">>>deleting of the created request");
        evm.deleteEventRequest(request);
        addExclusionFilter(request);

        debugee.quit();
    }

    private void addExclusionFilter(ClassUnloadRequest request) {
        display(">>>adding an exclusion class filter");
        display("");
        try {
            request.addClassExclusionFilter(pattern);
        } catch(InvalidRequestStateException e) {
            display(">>>>>EXPECTED InvalidRequestStateException");
        } catch(Exception e) {
            complain("******UNEXPECTED " + e);
            exitStatus = Consts.TEST_FAILED;
        }
        display("");
    }

}
