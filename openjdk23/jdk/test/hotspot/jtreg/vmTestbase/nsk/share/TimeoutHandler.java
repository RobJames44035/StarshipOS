/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.share;

import java.io.*;

/**
 * This class can be used to set timeout for test execution.
 */
public class TimeoutHandler {

    /**
     * Test execution timeout in minutes.
     */
    private int waitTime;

    /**
     * Make new <code>TimeoutHandler</code> object for timeout value
     * specified in command line arguments.
     */
    public TimeoutHandler(ArgumentParser argumentHandler) {
        this.waitTime = argumentHandler.getWaitTime();
    }

    /**
     * Perform test execution in separate thread and wait for
     * thread finishes or timeout exceeds.
     */
    public void runTest(Thread testThread) {
        long millisec = waitTime * 60 * 1000;
        testThread.start();
        try {
            testThread.join(millisec);
        } catch (InterruptedException ex) {
            throw new Failure(ex);
        }
    }

}
