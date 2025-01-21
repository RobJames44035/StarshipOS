/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

import nsk.share.runner.RunParams;

/**
 *  Helper that prints information about AllMemoryObjects.
 */
public class AllDiag implements Runnable {
        private long sleepTime;

        public AllDiag() {
                this(RunParams.getInstance().getSleepTime());
        }

        public AllDiag(long sleepTime) {
                this.sleepTime = sleepTime;
        }

        public void run() {
                AllMemoryObject.dumpStatistics();
                // Ensure that interrupt status is not lost
                if (Thread.currentThread().isInterrupted())
                        return;
                try {
                        Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                }
        }
}
