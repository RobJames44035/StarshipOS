/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jdi.MethodExitEvent.returnValue.returnValue001;

import nsk.share.TestBug;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/*
 * Debuggee class, handles commands for starting and stoping ForceEarlyReturnTestThread.
 */
public class returnValue001a extends AbstractJDIDebuggee {

    public static void main(String args[]) {
        new returnValue001a().doTest(args);
    }

    public static final String COMMAND_START_TEST_THREAD = "COMMAND_START_TEST_THREAD";

    public static final String COMMAND_STOP_TEST_THREAD = "COMMAND_STOP_TEST_THREAD";

    public String[] doInit(String args[]) {
        args = super.doInit(args);

        try {
            // load ForceEarlyReturnTestThread class to let debugger get instance of ReferenceType for this class
            Class.forName(ForceEarlyReturnTestThread.class.getName());
        } catch (Throwable t) {
            setSuccess(false);
            System.out.println("Unexpected exception during initialization: " + t);
            t.printStackTrace();
            throw new TestBug("Unexpected exception during initialization: " + t);
        }

        return args;
    }

    private ForceEarlyReturnTestThread testThread;

    public boolean parseCommand(String command) {
        if (super.parseCommand(command))
            return true;

        if (command.equals(COMMAND_START_TEST_THREAD)) {

            if (testThread != null)
                throw new TestBug("Thread is already created");

            testThread = new ForceEarlyReturnTestThread(log, false, 1);
            testThread.start();
            testThread.startExecuion();

            return true;
        } else if (command.equals(COMMAND_STOP_TEST_THREAD)) {

            if (testThread == null)
                throw new TestBug("Thread isn't created");

            testThread.stopExecution();
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
}
