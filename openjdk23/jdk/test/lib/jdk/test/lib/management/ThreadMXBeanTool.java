/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.management;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * A few utility methods to use ThreadMXBean.
 */
public final class ThreadMXBeanTool {

    /**
     * Waits until {@link Thread} is in the certain {@link Thread.State}
     * and blocking on {@code object}.
     *
     * @param state The thread state
     * @param object The object to block on
     */
    public static void waitUntilBlockingOnObject(Thread thread, Thread.State state, Object object)
        throws InterruptedException {
        String want = object == null ? null : object.getClass().getName() + '@'
                + Integer.toHexString(System.identityHashCode(object));
        ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
        while (thread.isAlive()) {
            ThreadInfo ti = tmx.getThreadInfo(thread.threadId());
            if (ti.getThreadState() == state
                    && (want == null || want.equals(ti.getLockName()))) {
                return;
            }
            Thread.sleep(1);
        }
    }

    /**
     * Waits until {@link Thread} is in native.
     */
    public static void waitUntilInNative(Thread thread) throws InterruptedException {
        ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
        while (thread.isAlive()) {
            ThreadInfo ti = tmx.getThreadInfo(thread.threadId());
            if (ti.isInNative()) {
                return;
            }
            Thread.sleep(1);
        }
    }

}
