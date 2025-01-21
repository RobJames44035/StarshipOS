/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.monitoring.share.thread;

import nsk.share.log.Log;
import java.lang.management.ThreadInfo;
import java.lang.management.MonitorInfo;
import java.lang.management.LockInfo;
import nsk.share.test.LocalRandom;
import nsk.share.TestBug;

/**
 * TimedWaitingThread is RecursiveMonitoringThread that is waiting on a lock.
 */
public class TimedWaitingThread extends RecursiveMonitoringThread {
        private String lock = new String("a lock");
        private volatile boolean ready = false;
        private Object readyLock = new Object();
        private int count = LocalRandom.nextInt(100) + 1;
        private volatile int n = 0;
        private static final String[] expectedMethods = {
                "java.lang.Object.wait",
                "java.lang.Object.wait0",
                "nsk.monitoring.share.thread.TimedWaitingThread.runInside"
        };

        public TimedWaitingThread(Log log, RunType recursionType, int maxDepth) {
                super(log, recursionType, maxDepth);
        }

        public void checkThreadInfo(ThreadInfo info) {
                super.checkThreadInfo(info);
                verify(info.getThreadState() == Thread.State.TIMED_WAITING, "ThreadInfo.getThreadState() = " + info.getThreadState() + " != " + Thread.State.TIMED_WAITING);
                //verify(info.getBlockedTime() == 0 || info.getBlockedTime() == -1, "ThreadInfo.getBlockedTime() == " + info.getBlockedTime());
                //verify(info.getBlockedCount() == count, "ThreadInfo.getBlockedCount() = " + info.getBlockedCount() + " != " + count);
                verify(info.getWaitedTime() == 0 || info.getWaitedTime() == -1, "ThreadInfo.getWaitedTime() == " + info.getWaitedTime());
                verify(info.getWaitedCount() == count + 1, "ThreadInfo.getWaitedCount() = " + info.getWaitedCount() + " != " + (count + 1));
                checkLockInfo(info.getLockInfo());
                verify(info.getLockName().equals(info.getLockInfo().toString()), "ThreadInfo.getLockName() = " + info.getLockName() + " != ThreadInfo.getLockInfo().toString() = " + info.getLockInfo().toString());
                verify(info.getLockOwnerId() == -1, "ThreadInfo.getLockOwnerId() = " + info.getLockOwnerId() + " != -1");
                verify(info.getLockOwnerName() == null, "ThreadInfo.getLockOwnerName() = " + info.getLockOwnerName() + " != null");
                checkMonitorInfo(info.getLockedMonitors(), null);
                checkSynchronizers(info.getLockedSynchronizers(), null);
        }

        private void checkLockInfo(LockInfo lockInfo) {
                verify(lockInfo.getClassName().equals(lock.getClass().getName()), "LockInfo.getClassName() = " + lockInfo.getClassName() + " differs from lock.getClass().getName() = " + lock.getClass().getName());
                verify(lockInfo.getIdentityHashCode() == System.identityHashCode(lock), "LockInfo.getIdentityHashCode() = " + lockInfo.getIdentityHashCode() + " differs from System.identityHashCode(lock) = " + System.identityHashCode(lock));
                String expectedToString = lock.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(lock));
                verify(lockInfo.toString().equals(expectedToString), "LockInfo.toString() = " + lockInfo.toString() + " differs from expected toString() = " + expectedToString);
        }

        public void waitState() {
                synchronized (readyLock) {
                        while (!ready) {
                                try {
                                        readyLock.wait();
                                } catch (InterruptedException e) {
                                        log.warn(e);
                                }
                        }
                }
                waitThreadState(Thread.State.TIMED_WAITING);
        }

        public void finish() {
                ready = false;
                synchronized (lock) {
                        lock.notify();
                }
        }

        protected void runInside() {
                synchronized (lock) {
                        do {
                                try {
                                        for (int i = 0; i < count; ++i)
                                                lock.wait(1);
                                        synchronized (readyLock) {
                                                ready = true;
                                                readyLock.notifyAll();
                                        }
                                        lock.wait(10000000);
                                } catch (InterruptedException e) {
                                        throw new TestBug(e);
                                }
                        } while (ready);
                }
        }

        protected boolean isStackTraceElementExpected(StackTraceElement element) {
                return super.isStackTraceElementExpected(element) || checkStackTraceElement(element, expectedMethods);
        }
}
