/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jdi.MethodExitEvent.returnValue.returnValue003;

import nsk.share.*;
import nsk.share.jpda.ForceEarlyReturnTestThread;
import nsk.share.jdi.*;

/*
 * Debuggee class, handles commands for starting and stoping ForceEarlyReturnTestThread.
 */
public class returnValue003a extends AbstractJDIDebuggee {
    static {
        try {
            // load thread class to let debugger get ReferenceType for TestThread class
            Class.forName(ForceEarlyReturnTestThread.class.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException while loading test thread class: " + e);
            e.printStackTrace(System.out);
            System.exit(Consts.JCK_STATUS_BASE + Consts.TEST_FAILED);
        }
    }

    public static void main(String args[]) {
        new returnValue003a().doTest(args);
    }

    // start and suspend test threads to let debugger initialize breakpoints (debugger should obtain ThreadReference)
    // command:threadsNumber:iterationsNumber
    public static final String COMMAND_START_AND_SUSPEND_TEST_THREAD = "startAndSuspendTestThread";

    // let test threads continue execution
    public static final String COMMAND_START_TEST_THREAD_EXECUTION = "startTestThreadExecution";

    // stop test threads
    public static final String COMMAND_STOP_TEST_THREAD = "stopTestThread";

    public static final String testThreadName = "returnValue03_TestThread";

    public boolean parseCommand(String command) {
        if (super.parseCommand(command))
            return true;

        if (command.equals(COMMAND_START_AND_SUSPEND_TEST_THREAD)) {
            startTestThread();
            return true;
        } else if (command.equals(COMMAND_START_TEST_THREAD_EXECUTION)) {
            startTestThreadsExecution();
            return true;
        } else if (command.equals(COMMAND_STOP_TEST_THREAD)) {
            stopTestThreads();
            return true;
        }

        return false;
    }

    private ForceEarlyReturnTestThread testThread;

    private void startTestThread() {
        testThread = new ForceEarlyReturnTestThread(log, true, 1);
        testThread.setName(testThreadName);
        testThread.start();
    }

    private void startTestThreadsExecution() {
        if (testThread == null) {
            throw new TestBug("Test threads wasn't started");
        }
        testThread.startExecuion();
    }

    private void stopTestThreads() {
        if (testThread == null) {
            throw new TestBug("Test threads wasn't started");
        }

        testThread.stopExecution();

        try {
            testThread.join();
        } catch (InterruptedException e) {
            setSuccess(false);
            log.complain("Unexpected exception: " + e);
            e.printStackTrace(log.getOutStream());
        }

        if (!testThread.getSuccess())
            setSuccess(false);
    }

    // access to success status for TestThread
    public void setSuccess(boolean value) {
        super.setSuccess(value);
    }
}
