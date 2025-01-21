/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package com.sun.management.internal;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import javax.management.ObjectName;
import jdk.management.VirtualThreadSchedulerMXBean;
import jdk.internal.access.JavaLangAccess;
import jdk.internal.access.SharedSecrets;
import jdk.internal.vm.ContinuationSupport;
import sun.management.Util;

/**
 * Provides the implementation of the management interface for the JDK's default virtual
 * thread scheduler.
 */
public class VirtualThreadSchedulerImpls {
    private VirtualThreadSchedulerImpls() {
    }

    public static VirtualThreadSchedulerMXBean create() {
        if (ContinuationSupport.isSupported()) {
            return new VirtualThreadSchedulerImpl();
        } else {
            return new BoundVirtualThreadSchedulerImpl();
        }
    }

    /**
     * Base implementation of VirtualThreadSchedulerMXBean.
     */
    private abstract static class BaseVirtualThreadSchedulerImpl
            implements VirtualThreadSchedulerMXBean {

        @Override
        public final ObjectName getObjectName() {
            return Util.newObjectName("jdk.management:type=VirtualThreadScheduler");
        }

        @Override
        public String toString() {
            var sb = new StringBuilder("[parallelism=");
            sb.append(getParallelism());
            append(sb, "size", getPoolSize());
            append(sb, "mounted", getMountedVirtualThreadCount());
            append(sb, "queued", getQueuedVirtualThreadCount());
            sb.append(']');
            return sb.toString();
        }

        private void append(StringBuilder sb, String name, long value) {
            sb.append(", ").append(name).append('=');
            if (value >= 0) {
                sb.append(value);
            } else {
                sb.append("<unavailable>");
            }
        }
    }

    /**
     * Implementation of VirtualThreadSchedulerMXBean when virtual threads are
     * implemented with continuations + scheduler.
     */
    private static final class VirtualThreadSchedulerImpl extends BaseVirtualThreadSchedulerImpl {
        /**
         * Holder class for scheduler.
         */
        private static class Scheduler {
            private static final Executor scheduler =
                SharedSecrets.getJavaLangAccess().virtualThreadDefaultScheduler();
            static Executor instance() {
                return scheduler;
            }
        }

        @Override
        public int getParallelism() {
            if (Scheduler.instance() instanceof ForkJoinPool pool) {
                return pool.getParallelism();
            }
            throw new InternalError();  // should not get here
        }

        @Override
        public void setParallelism(int size) {
            if (Scheduler.instance() instanceof ForkJoinPool pool) {
                pool.setParallelism(size);
                if (pool.getPoolSize() < size) {
                    // FJ worker thread creation is on-demand
                    Thread.startVirtualThread(() -> { });
                }

                return;
            }
            throw new UnsupportedOperationException();  // should not get here
        }

        @Override
        public int getPoolSize() {
            if (Scheduler.instance() instanceof ForkJoinPool pool) {
                return pool.getPoolSize();
            }
            return -1;  // should not get here
        }

        @Override
        public int getMountedVirtualThreadCount() {
            if (Scheduler.instance() instanceof ForkJoinPool pool) {
                return pool.getActiveThreadCount();
            }
            return -1;  // should not get here
        }

        @Override
        public long getQueuedVirtualThreadCount() {
            if (Scheduler.instance() instanceof ForkJoinPool pool) {
                return pool.getQueuedTaskCount() + pool.getQueuedSubmissionCount();
            }
            return -1L;  // should not get here
        }
    }

    /**
     * Implementation of VirtualThreadSchedulerMXBean when virtual threads are backed
     * by platform threads.
     */
    private static final class BoundVirtualThreadSchedulerImpl extends BaseVirtualThreadSchedulerImpl {
        @Override
        public int getParallelism() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void setParallelism(int size) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getPoolSize() {
            return -1;
        }

        @Override
        public int getMountedVirtualThreadCount() {
            return -1;
        }

        @Override
        public long getQueuedVirtualThreadCount() {
            return -1L;
        }
    }
}

