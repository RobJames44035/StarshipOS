/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * @test
 * @bug 4331522
 * @summary addClassFilter("Foo") acts like "Foo*"
 * @author Robert Field/Jim Holmlund
 *
 * @run build TestScaffold VMConnection
 * @run compile -g HelloWorld.java
 * @run driver FilterNoMatch
 */

/* This tests the patternMatch function in JDK file:
 *    .../src/share/back/eventHandler.c
 *
 * This test verifies that patterns that do not match really don't.
 * See also testcase FilterMatch.java.
 */

import java.util.*;
import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

public class FilterNoMatch extends TestScaffold {

    EventSet eventSet = null;
    boolean stepCompleted = false;

    public static void main(String args[]) throws Exception {
        new FilterNoMatch(args).startTests();
    }

    public FilterNoMatch(String args[]) {
        super(args);
    }

    protected void runTests() throws Exception {
        /*
         * Get to the top of HelloWorld main() to determine referenceType and mainThread
         */
        BreakpointEvent bpe = startToMain("HelloWorld");
        ReferenceType referenceType = (ClassType)bpe.location().declaringType();
        mainThread = bpe.thread();
        // VM has started, but hasn't started running the test program yet.
        EventRequestManager requestManager = vm().eventRequestManager();

        Location location = findLocation(referenceType, 3);
        BreakpointRequest bpRequest = requestManager.createBreakpointRequest(location);

        try {
            addListener(this);
        } catch (Exception ex){
            ex.printStackTrace();
            failure("failure: Could not add listener");
            throw new Exception("FilterNoMatch: failed");
        }

        bpRequest.enable();

        StepRequest stepRequest = requestManager.createStepRequest(mainThread,
                                  StepRequest.STEP_LINE,StepRequest.STEP_OVER);

        // We have to filter out all these so that they don't cause the
        // listener to be called.
        stepRequest.addClassExclusionFilter("java.*");
        stepRequest.addClassExclusionFilter("javax.*");
        stepRequest.addClassExclusionFilter("sun.*");
        stepRequest.addClassExclusionFilter("com.sun.*");
        stepRequest.addClassExclusionFilter("com.oracle.*");
        stepRequest.addClassExclusionFilter("oracle.*");
        stepRequest.addClassExclusionFilter("jdk.internal.*");

        // We want our listener to be called if a pattern does not match.
        // So, here we want patterns that do not match HelloWorld.
        // If any pattern here erroneously matches, then our listener
        // will not get called and the test will fail.
        stepRequest.addClassExclusionFilter("H");
        stepRequest.addClassExclusionFilter("HelloWorl");
        stepRequest.addClassExclusionFilter("HelloWorldx");
        stepRequest.addClassExclusionFilter("xHelloWorld");

        stepRequest.addClassExclusionFilter("*elloWorldx");
        stepRequest.addClassExclusionFilter("*elloWorl");
        stepRequest.addClassExclusionFilter("*xHelloWorld");
        stepRequest.addClassExclusionFilter("elloWorld*");
        stepRequest.addClassExclusionFilter("HelloWorldx*");
        stepRequest.addClassExclusionFilter("xHelloWorld*");

        // As a test, uncomment this line and this test should fail.
        //stepRequest.addClassExclusionFilter("*elloWorld");

        stepRequest.enable();

        vm().resume();

        waitForVMDisconnect();

        if (!stepCompleted) {
            throw new Exception( "Failed: .");
        }
        System.out.println( "Passed: ");
    }

    // ****************  event handlers **************

    public void eventSetReceived(EventSet set) {
        this.eventSet = set;
    }

    // This gets called if no patterns match.  If any
    // pattern is erroneously matched, then this method
    // will not get called.
    public void stepCompleted(StepEvent event) {
        stepCompleted = true;
        System.out.println("StepEvent at" + event.location());
        // disable the step and then run to completion
        StepRequest str = (StepRequest)event.request();
        str.disable();
        eventSet.resume();
    }

    public void breakpointReached(BreakpointEvent event) {
        System.out.println("BreakpointEvent at" + event.location());
        BreakpointRequest bpr = (BreakpointRequest)event.request();
        // The bkpt was hit; disable it.
        bpr.disable();
    }
}
