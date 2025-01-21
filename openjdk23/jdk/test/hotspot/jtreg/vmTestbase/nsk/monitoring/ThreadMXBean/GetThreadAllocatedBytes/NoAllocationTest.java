/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package nsk.monitoring.ThreadMXBean.GetThreadAllocatedBytes;

import nsk.share.*;
import nsk.monitoring.share.*;
import nsk.monitoring.ThreadMXBean.ThreadMXBeanTestBase;
import nsk.monitoring.ThreadMXBean.MXBeanTestThread;
import nsk.monitoring.ThreadMXBean.BarrierHandler;

/**
 * Tests getThreadAllocatedBytes(long id) function of com.sun.management.ThreadMXBean
 * <p>
 *  - Test starts thread that does not allocate any additional memory and stores
 * it's getThreadAllocatedBytes() result (value1)
 * Then it starts several other threads that does allocate memory and, after these
 * threads are finished, checks that getThreadAllocatedBytes() result (value2)
 * does not differ from value1
 */
public class NoAllocationTest extends ThreadMXBeanTestBase {
    private volatile boolean start = false;
    private volatile boolean stop = false;

    /**
     * Actually runs the test
     */
    public void run() {
        if (threadMXBean == null)
            return;
        // No allocation TestThread thread.
        // Run behavior : does nothing,  waits for notify() call
        MXBeanTestThread tr = new MXBeanTestThread() {
                @Override
                public void doWork() {
                    start = true;
                    while (!stop) { /* empty */ }
                }
            };
        tr.start();
        MXBeanTestThread tr1 = new MXBeanTestThread();
        MXBeanTestThread tr2 = new MXBeanTestThread();
        BarrierHandler handler = startThreads(tr1, tr2);
        while (!start) { /* empty */ }
        long value1 = threadMXBean.getThreadAllocatedBytes(tr.getId());
        handler.proceed();
        long value2 = threadMXBean.getThreadAllocatedBytes(tr.getId());
        if (value1 != value2) {
            throw new TestFailure("Failure! It is expected that idle thread "
                                  + "does not allocate any memory. getThreadAllocatedBytes() call "
                                  + "for idle TestThread-" + tr.getName() + " returns different "
                                  + "values. Recieved : " + value1 + " and " + value2);
        }
        log.info("NoAllocationTest passed.");
        stop = true;
        handler.finish();
    }

    /**
     * Entry point for java program
     * @param args sets the test configuration
     */
    public static void main(String[] args) {
        ThreadMXBeanTestBase test = new NoAllocationTest();
        Monitoring.runTest(test, test.setGarbageProducer(args));
    }
}
