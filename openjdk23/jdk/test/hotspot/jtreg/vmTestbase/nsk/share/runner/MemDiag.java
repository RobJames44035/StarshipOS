/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.runner;

/**
 *  Helper that prints memory information.
 */
public class MemDiag implements Runnable {
        private long sleepTime;

        public MemDiag() {
                this(RunParams.getInstance().getSleepTime());
        }

        public MemDiag(long sleepTime) {
                this.sleepTime = sleepTime;
        }

        public void run() {
                System.out.println(Runtime.getRuntime().freeMemory());
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
