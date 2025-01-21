/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jdi.StackFrame.getArgumentValues.getArgumentValues003;

import nsk.share.TestBug;
import nsk.share.jdi.*;

//Debuggee class, handles command to start and stop test thread
public class getArgumentValues003a extends AbstractJDIDebuggee {

    public static final String COMMAND_START_TEST_THREAD = "COMMAND_START_TEST_THREAD";

    public static final String COMMAND_STOP_TEST_THREAD = "COMMAND_STOP_TEST_THREAD";

    public static final String testThreadName = "getArgumentValues003a_TestThread";

    class TestThread extends Thread {

        volatile boolean stackWasCreated;

        volatile boolean stoped;

        public TestThread() {
            super(getArgumentValues003a.testThreadName);
        }

        public void run() {
            stackWasCreated = true;

            while (!stoped);
        }
    }

    private TestThread testThread;

    public boolean parseCommand(String command) {
        if (super.parseCommand(command))
            return true;

        if (command.equals(COMMAND_START_TEST_THREAD)) {

            if (testThread != null)
                throw new TestBug("Thread is already created");

            testThread = new TestThread();
            testThread.start();

            while (!testThread.stackWasCreated)
                Thread.yield();

            return true;
        } else if (command.equals(COMMAND_STOP_TEST_THREAD)) {

            if (testThread == null)
                throw new TestBug("Thread isn't created");

            testThread.stoped = true;

            try {
                testThread.join();
            } catch (InterruptedException e) {
                setSuccess(false);
                log.complain("Unexpected exception: " + e);
                e.printStackTrace(log.getOutStream());
            }

            return true;
        }

        return false;
    }

    public static void main(String args[]) {
        new getArgumentValues003a().doTest(args);
    }

}
