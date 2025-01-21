/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach041;

import nsk.share.aod.TargetApplicationWaitingAgents;

public class attach041Target extends TargetApplicationWaitingAgents {

    static class TestThread extends Thread {
        TestThread(ThreadGroup threadGroup, String name) {
            super(threadGroup, name);
        }

        public void run() {
            try {
                log.display(Thread.currentThread() + " is running");
            } catch (Throwable t) {
                setStatusFailed("Unexpected exception: " + t);
                t.printStackTrace(log.getOutStream());
            }
        }
    }

    /*
     * NOTE: constant value should be up-to-date with constants in the agent's code
     */

    static final String STARTED_TEST_THREAD_NAME = "attach041-TestThread";

    protected void targetApplicationActions() throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("attach041-TestThreadGroup");

        TestThread thread = new TestThread(threadGroup, STARTED_TEST_THREAD_NAME);
        thread.setName(STARTED_TEST_THREAD_NAME);
        thread.start();
    }

    public static void main(String[] args) {
        new attach041Target().runTargetApplication(args);
    }
}
