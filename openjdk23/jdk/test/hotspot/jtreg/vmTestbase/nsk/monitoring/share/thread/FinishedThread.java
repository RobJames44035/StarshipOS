/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.monitoring.share.thread;

import nsk.share.log.Log;
import java.lang.management.ThreadInfo;
import nsk.share.TestFailure;
import nsk.share.TestBug;

/**
 * Finished Thread is dummy RecursiveMonitoringThread that is getting
 * started and finished.
 */
public class FinishedThread extends RecursiveMonitoringThread {
        public FinishedThread(Log log, RunType recursionType, int maxDepth) {
                super(log, recursionType, maxDepth);
        }

        public void waitState() {
                try {
                        if (runner != null)
                                runner.join();
                } catch (InterruptedException e) {
                        log.warn(e);
                }
        }

        public void finish() {
        }

        protected void runInside() {
        }

        public void checkThreadInfo(ThreadInfo info) {
                verify(info == null, "ThreadInfo != null");
        }
}
