/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug 6485605
 * @summary com.sun.jdi.InternalException: Inconsistent suspend policy in internal event handler
 * @author jjh
 *
 * @run build TestScaffold VMConnection TargetListener TargetAdapter
 * @run compile -g SuspendThreadTest.java
 * @run driver SuspendThreadTest
 */
import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;


    /********** target program **********/

class SuspendThreadTarg {
    public static long count;
    public static boolean active = true;

    public static void bkpt() {
        count++;
    }

    public static void main(String[] args){
        System.out.println("Howdy!");

        // We need this to be running so the bkpt
        // can be hit immediately when it is enabled
        // in the back-end.
        while(active) {
            bkpt();
        }
        System.out.println("Goodbye from SuspendThreadTarg, count = " + count);
    }
}

    /********** test program **********/

public class SuspendThreadTest extends TestScaffold {
    ClassType targetClass;
    ThreadReference mainThread;

    SuspendThreadTest (String args[]) {
        super(args);
    }

    public static void main(String[] args)      throws Exception {
        new SuspendThreadTest(args).startTests();
    }

    /********** event handlers **********/

    // 1000 makes the test take over 2 mins on win32
    static int maxBkpts = 200;
    volatile int bkptCount;
    // to guard against spurious wakeups from bkptSignal.wait()
    boolean signalSent;
    // signal that a breakpoint has happened
    final private Object bkptSignal = new Object() {};
    BreakpointRequest bkptRequest;
    Field debuggeeCountField, debuggeeActiveField;

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
        synchronized (bkptSignal) {
            if (bkptCount++ < maxBkpts) {
                bkptRequest.enable();
            }
            signalSent = true;
            bkptSignal.notifyAll();
        }
    }

    public void vmDisconnected(VMDisconnectEvent event) {
        println("Got VMDisconnectEvent");
    }

    /********** test core **********/

    protected void runTests() throws Exception {
        try {
            /*
             * Get to the top of main()
             * to determine targetClass and mainThread
             */
            BreakpointEvent bpe = startToMain("SuspendThreadTarg");
            targetClass = (ClassType)bpe.location().declaringType();
            mainThread = bpe.thread();
            EventRequestManager erm = vm().eventRequestManager();

            Location loc1 = findMethod(targetClass, "bkpt", "()V").location();

            bkptRequest = erm.createBreakpointRequest(loc1);

            // Without this, it is a SUSPEND_ALL bkpt and the test will pass
            bkptRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
            bkptRequest.enable();

            debuggeeCountField = targetClass.fieldByName("count");
            debuggeeActiveField = targetClass.fieldByName("active");
            try {
                addListener (this);
            } catch (Exception ex){
                ex.printStackTrace();
                failure("failure: Could not add listener");
                throw new Exception("SuspendThreadTest: failed", ex);
            }

            int prevBkptCount;
            vm().resume();
            synchronized (bkptSignal) {
                while (bkptCount < maxBkpts) {
                    prevBkptCount = bkptCount;
                    // If we don't get a bkpt within 5 secs,
                    // the test fails
                    signalSent = false;
                    do {
                        try {
                            bkptSignal.wait(5000);
                        } catch (InterruptedException ee) {
                        }
                    } while (signalSent == false);
                    if (prevBkptCount == bkptCount) {
                        failure("failure: test hung");
                        break;
                    }
                }
            }
            println("done with loop");
            bkptRequest.disable();
            removeListener(this);

            /*
             * deal with results of test
             * if anything has called failure("foo") testFailed will be true
             */
            if (!testFailed) {
                println("SuspendThreadTest: passed");
            } else {
                throw new Exception("SuspendThreadTest: failed");
            }
        } finally {
            if (targetClass != null && debuggeeActiveField != null) {
                targetClass.setValue(debuggeeActiveField, vm().mirrorOf(false));
            }
        }
    }
}
