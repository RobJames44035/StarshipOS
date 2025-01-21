/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

// TEMPLATE: global replace "Template" with your test name
// TEMPLATE: change bug number and fill out <SUMMARY> and <AUTHOR>
// TEMPLATE: delete TEMPLATE lines
/**
 * @test
 * @bug 0000000
 * @summary <SUMMARY>
 * @author <AUTHOR>
 *
 * @run build TestScaffold VMConnection TargetListener TargetAdapter
 * @run compile -g TemplateTest.java
 * @run driver TemplateTest
 */
import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

import java.util.*;

    /********** target program **********/

class TemplateTarg {
    public static void main(String[] args){
        System.out.println("Howdy!");
        System.out.println("Goodbye from TemplateTarg!");
    }
}

    /********** test program **********/

public class TemplateTest extends TestScaffold {
    ReferenceType targetClass;
    ThreadReference mainThread;

    TemplateTest (String args[]) {
        super(args);
    }

    public static void main(String[] args)      throws Exception {
        new TemplateTest(args).startTests();
    }

    /********** event handlers **********/

// TEMPLATE: delete the handlers you don't need
// TEMPLATE: defaults are in TargetAdapter

    public void eventSetReceived(EventSet set) {
        println("Got event set");
    }

    public void eventReceived(Event event) {
        println("Got event");
    }

    public void breakpointReached(BreakpointEvent event) {
        println("Got BreakpointEvent");
    }

    public void exceptionThrown(ExceptionEvent event) {
        println("Got ExceptionEvent");
    }

    public void stepCompleted(StepEvent event) {
        println("Got StepEvent");
    }

    public void classPrepared(ClassPrepareEvent event) {
        println("Got ClassPrepareEvent");
    }

    public void classUnloaded(ClassUnloadEvent event) {
        println("Got ClassUnloadEvent");
    }

    public void methodEntered(MethodEntryEvent event) {
        println("Got MethodEntryEvent");
    }

    public void methodExited(MethodExitEvent event) {
        println("Got MethodExitEvent");
    }

    public void fieldAccessed(AccessWatchpointEvent event) {
        println("Got AccessWatchpointEvent");
    }

    public void fieldModified(ModificationWatchpointEvent event) {
        println("Got ModificationWatchpointEvent");
    }

    public void threadStarted(ThreadStartEvent event) {
        println("Got ThreadStartEvent");
    }

    public void threadDied(ThreadDeathEvent event) {
        println("Got ThreadDeathEvent");
    }

    public void vmStarted(VMStartEvent event) {
        println("Got VMStartEvent");
    }

    public void vmDied(VMDeathEvent event) {
        println("Got VMDeathEvent");
    }

    public void vmDisconnected(VMDisconnectEvent event) {
        println("Got VMDisconnectEvent");
    }

    /********** test core **********/

    protected void runTests() throws Exception {
        /*
         * Get to the top of main()
         * to determine targetClass and mainThread
         */
        BreakpointEvent bpe = startToMain("TemplateTarg");
        targetClass = bpe.location().declaringType();
        mainThread = bpe.thread();
        EventRequestManager erm = vm().eventRequestManager();

// TEMPLATE: set things up

// TEMPLATE: for example
        /*
         * Set event requests
         */
        StepRequest request = erm.createStepRequest(mainThread,
                                                    StepRequest.STEP_LINE,
                                                    StepRequest.STEP_OVER);
        request.enable();

        /*
         * resume the target listening for events
         */
        listenUntilVMDisconnect();

        /*
         * deal with results of test
         * if anything has called failure("foo") testFailed will be true
         */
        if (!testFailed) {
            println("TemplateTest: passed");
        } else {
            throw new Exception("TemplateTest: failed");
        }
    }
}
