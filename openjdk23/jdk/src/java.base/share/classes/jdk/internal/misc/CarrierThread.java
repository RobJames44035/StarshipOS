/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.internal.misc;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import jdk.internal.access.JavaLangAccess;
import jdk.internal.access.JavaUtilConcurrentFJPAccess;
import jdk.internal.access.SharedSecrets;
import jdk.internal.vm.Continuation;

/**
 * A ForkJoinWorkerThread that can be used as a carrier thread.
 */
public class CarrierThread extends ForkJoinWorkerThread {
    private static final JavaLangAccess JLA = SharedSecrets.getJavaLangAccess();
    private static final Unsafe U = Unsafe.getUnsafe();

    private static final ThreadGroup CARRIER_THREADGROUP = carrierThreadGroup();

    private static final long CONTEXTCLASSLOADER;
    private static final long INHERITABLETHREADLOCALS;

    // compensating state
    private static final int NOT_COMPENSATING = 0;
    private static final int COMPENSATE_IN_PROGRESS = 1;
    private static final int COMPENSATING = 2;
    private int compensating;

    // FJP value to adjust release counts
    private long compensateValue;

    @SuppressWarnings("this-escape")
    public CarrierThread(ForkJoinPool pool) {
        super(CARRIER_THREADGROUP, pool, true);
        U.putReference(this, CONTEXTCLASSLOADER, ClassLoader.getSystemClassLoader());
        U.putReference(this, INHERITABLETHREADLOCALS, null);
    }

    /**
     * Mark the start of a blocking operation.
     */
    public boolean beginBlocking() {
        assert Thread.currentThread().isVirtual() && JLA.currentCarrierThread() == this;
        assert compensating == NOT_COMPENSATING || compensating == COMPENSATING;

        if (compensating == NOT_COMPENSATING) {
            // don't preempt when attempting to compensate
            Continuation.pin();
            try {
                compensating = COMPENSATE_IN_PROGRESS;

                // Uses FJP.tryCompensate to start or re-activate a spare thread
                compensateValue = ForkJoinPools.beginCompensatedBlock(getPool());
                compensating = COMPENSATING;
                return true;
            } catch (Throwable e) {
                // exception starting spare thread
                compensating = NOT_COMPENSATING;
                throw e;
            } finally {
                Continuation.unpin();
            }
        } else {
            return false;
        }
    }

    /**
     * Mark the end of a blocking operation.
     */
    public void endBlocking() {
        assert Thread.currentThread() == this || JLA.currentCarrierThread() == this;
        if (compensating == COMPENSATING) {
            ForkJoinPools.endCompensatedBlock(getPool(), compensateValue);
            compensating = NOT_COMPENSATING;
            compensateValue = 0;
        }
    }

    @Override
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler ueh) { }

    @Override
    public void setContextClassLoader(ClassLoader cl) {
        throw new SecurityException("setContextClassLoader");
    }

    /**
     * The thread group for the carrier threads.
     */
    private static ThreadGroup carrierThreadGroup() {
        ThreadGroup group = JLA.currentCarrierThread().getThreadGroup();
        for (ThreadGroup p; (p = group.getParent()) != null; )
            group = p;
        var carrierThreadsGroup = new ThreadGroup(group, "CarrierThreads");
        return carrierThreadsGroup;
    }

    /**
     * Defines static methods to invoke non-public ForkJoinPool methods via the
     * shared secret support.
     */
    private static class ForkJoinPools {
        private static final JavaUtilConcurrentFJPAccess FJP_ACCESS =
                SharedSecrets.getJavaUtilConcurrentFJPAccess();
        static long beginCompensatedBlock(ForkJoinPool pool) {
            return FJP_ACCESS.beginCompensatedBlock(pool);
        }
        static void endCompensatedBlock(ForkJoinPool pool, long post) {
            FJP_ACCESS.endCompensatedBlock(pool, post);
        }
    }

    static {
        CONTEXTCLASSLOADER = U.objectFieldOffset(Thread.class,
                "contextClassLoader");
        INHERITABLETHREADLOCALS = U.objectFieldOffset(Thread.class,
                "inheritableThreadLocals");
    }
}
