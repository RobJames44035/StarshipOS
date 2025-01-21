/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

import nsk.share.runner.RunParams;

/**
 *  Helper that prints information about FinMemoryObjects.
 */
public class FinDiag implements Runnable {
        private long sleepTime;

        public FinDiag() {
                this(RunParams.getInstance().getSleepTime());
        }

        public FinDiag(long sleepTime) {
                this.sleepTime = sleepTime;
        }

        public void run() {
                FinMemoryObject.dumpStatistics();
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
