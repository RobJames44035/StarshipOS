/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * @test
 * @bug 6496524
 * @key intermittent
 * @summary Setting breakpoint in jdb crashes Hotspot JVM
 * @author jjh
 *
 * @run build TestScaffold VMConnection TargetListener TargetAdapter
 * @run compile -g BreakpointTest.java
 * @run driver BreakpointTest
 */

import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

import java.util.*;

// The debuggee just runs in a loop. The debugger
// sets a bkpt on the Math.random call.  When the
// bkpt is hit, the debugger disables it, resumes
// the debuggee, waits a bit, and enables the bkpt again.

class BreakpointTarg {
    public final static int BKPT_LINE = 56;

    public static long count;
    static void doit() {
        Object[] roots = new Object[200000];
        while (true) {
            int index = (int) (Math.random() * roots.length); // BKPT_LINE
            // This println makes the test pass
            //System.out.println("Debuggee: index = " + index);
            roots[index] = new Object();   // bkpt here passes
                                           // and null instead of new Object()
                                           // passes
            count++;
        }
    }

    public static void main(String[] args) {
        doit();
    }
}

    /********** test program **********/

public class BreakpointTest extends TestScaffold {
    ClassType targetClass;
    ThreadReference mainThread;

    BreakpointTest (String args[]) {
        super(args);
    }

    public static void main(String[] args)      throws Exception {
        new BreakpointTest(args).startTests();
    }

    /********** event handlers **********/

    static int maxBkpts = 50;
    int bkptCount;
    BreakpointRequest bkptRequest;
    Field debuggeeCountField;

    // When we get a bkpt we want to disable the request,
    // resume the debuggee, and then re-enable the request
    public void breakpointReached(BreakpointEvent event) {
        System.out.println("Got BreakpointEvent: " + bkptCount +
                           ", debuggeeCount = " +
                           ((LongValue)targetClass.
                            getValue(debuggeeCountField)).value()
                           );
        bkptRequest.disable();
    }

    public void eventSetComplete(EventSet set) {
        set.resume();

        // The main thread watchs the bkptCount to
        // see if bkpts stop coming in.  The
        // test _should_ fail well before maxBkpts bkpts.
        if (bkptCount++ < maxBkpts) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ee) {
            }
            bkptRequest.enable();
        }
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
        BreakpointEvent bpe = startToMain("BreakpointTarg");
        targetClass = (ClassType)bpe.location().declaringType();
        mainThread = bpe.thread();
        EventRequestManager erm = vm().eventRequestManager();

        Location loc1 = findLocation(
                            targetClass,
                            BreakpointTarg.BKPT_LINE);

        bkptRequest = erm.createBreakpointRequest(loc1);
        bkptRequest.enable();
        debuggeeCountField = targetClass.fieldByName("count");
        try {

            addListener (this);
        } catch (Exception ex){
            ex.printStackTrace();
            failure("failure: Could not add listener");
            throw new Exception("BreakpointTest: failed");
        }

        int prevBkptCount;
        vm().resume();
        while (!vmDisconnected && bkptCount < maxBkpts) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ee) {
            }
        }

        println("done with loop, final count = " +
                    ((LongValue)targetClass.
                     getValue(debuggeeCountField)).value());
        bkptRequest.disable();
        removeListener(this);


        /*
         * deal with results of test
         * if anything has called failure("foo") testFailed will be true
         */
        if (!testFailed) {
            println("BreakpointTest: passed");
        } else {
            throw new Exception("BreakpointTest: failed");
        }
    }
}
