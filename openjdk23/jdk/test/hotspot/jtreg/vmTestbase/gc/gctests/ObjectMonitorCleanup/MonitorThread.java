/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package gc.gctests.ObjectMonitorCleanup;

import nsk.share.test.ExecutionController;

/**
 * Helper thread class for ObjectMonitorCleanup class
 */
public class MonitorThread extends Thread {

    /**
     * Object used for synchronization between threads in the test.
     */
    public static volatile Object otherObject;
    /**
     * Simple way for the test to check if the running thread completed
     * it's work or not.
     */
    public boolean completedOk;
    /**
     * Tells the worker thread if it should keep running or if
     * it should terminate.
     */
    public volatile boolean keepRunning;
    private ExecutionController stresser;

    /**
     * Constructor for the thread.
     *
     * @param maxRunTimeMillis Maximum time in milliseconds that
     * the thread should run.
     */
    public MonitorThread(ExecutionController stresser) {
        this.stresser = stresser;
        this.otherObject = new Object(); /* avoid null on first reference */
    }

    /**
     * Main entry point for the thread.
     */
    public final void run() {
        synchronized (this) {
            completedOk = false;
            keepRunning = true;
        }

        // Do we need to lock keepRunning before we check it?
        while (keepRunning
                && stresser.continueExecution()) {
            Object placeholder = otherObject;
            synchronized (placeholder) {
                placeholder.notifyAll();
            }
        }

        synchronized (this) {
            completedOk = keepRunning;
        }
    }
}
