/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.monitoring.share.thread;

import nsk.share.log.Log;
import java.lang.management.ThreadInfo;
import nsk.share.TestFailure;
import nsk.share.TestBug;

/**
 * New Thread is dummy RecursiveMonitoringThread that is not getting
 * started.
 */
public class NewThread extends RecursiveMonitoringThread {
        public NewThread(Log log, RunType recursionType, int maxDepth) {
                super(log, recursionType, maxDepth);
        }

        public void begin() {
                // We don't run this thread.
                runner = new Thread(this);
        }

        public void waitState() {
        }

        public void finish() {
        }

        protected void runInside() {
                throw new TestBug("Should not reach here");
        }

        protected boolean isStackTraceElementExpected(StackTraceElement element) {
                return super.isStackTraceElementExpected(element);
        }

        public void checkThreadInfo(ThreadInfo info) {
                verify(info == null, "ThreadInfo != null");
        }
}
