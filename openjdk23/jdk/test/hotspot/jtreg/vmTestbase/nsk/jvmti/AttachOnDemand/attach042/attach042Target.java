/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach042;

import nsk.share.aod.TargetApplicationWaitingAgents;

public class attach042Target extends TargetApplicationWaitingAgents {
    static class TestThread extends Thread {
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

    static final String STARTED_TEST_THREAD_NAME = "attach042-TestThread";

    protected void targetApplicationActions() throws InterruptedException {
        TestThread thread = new TestThread();
        thread.setName(STARTED_TEST_THREAD_NAME);
        thread.start();
    }

    public static void main(String[] args) {
        new attach042Target().runTargetApplication(args);
    }
}
