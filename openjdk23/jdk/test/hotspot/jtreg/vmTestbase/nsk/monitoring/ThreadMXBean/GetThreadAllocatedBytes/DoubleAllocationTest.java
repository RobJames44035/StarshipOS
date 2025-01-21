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
 *  - Test starts thread and allocates some amount of memory, then calculates this
 * allocation using getThreadAllocatedBytes() call (allocation1). Then this thread
 * allocates just the same amount of memory and calculates getThreadAllocatedBytes()
 * again (allocation2). It is assumed that allocation2/allocation1 ~ 2.
 */
public class DoubleAllocationTest extends ThreadMXBeanTestBase {

    /**
     * Actually runs the test
     */
    public void run() {
        if (threadMXBean == null)
            return;
        //TestThread tr = new DoubleAllocationTestThread();

        // Double allocation TestThread thread.
        // Run behavior : Allocates memory, waits for notify() call,
        // allocates memory again and waits for notify() call again
        MXBeanTestThread.warmUp(garbageProducerId);
        MXBeanTestThread tr = new MXBeanTestThread(garbageProducerId) {
            @Override
            public void doWork() {
                //threadStartBytes = threadMXBean.getThreadAllocatedBytes(Thread.currentThread().getId());
                //allocate();
                handler.ready();
                allocate();
                handler.ready();
                allocate();
                handler.ready();
            }
        };
        BarrierHandler handler = startThreads(tr);
        try {
            long startBytes = threadMXBean.getThreadAllocatedBytes(tr.getId());
            handler.proceed();
            long value1 = threadMXBean.getThreadAllocatedBytes(tr.getId())
                    - startBytes;
            handler.proceed();
            long value2 = threadMXBean.getThreadAllocatedBytes(tr.getId())
                    - startBytes;
            // Expect value1 and value2 differs not more then for 15%
            if (Math.abs(((double) value2 / (double) value1) - 2) > (double)2*DELTA_PERCENT/100)
            //if ( Math.abs(2*value1 - value2) > value1*DELTA_PERCENT/100)
                throw new TestFailure("Failure! Expected getThreadAllocatedBytes() "
                        + "measurement for some thread at one moment could not be "
                        + "greater then similar measurement for the same thread "
                        + "at later moment. Thread allocates same amount of memory "
                        + "before each measurement. Excpected ~2 times difference. "
                        + "Recieved: " + value1 + " and " + value2);
            log.info("DoubleAllocationTest passed.");
        } finally {
            handler.finish();
        }
    }

    /**
     * Entry point for java program
     * @param args sets the test configuration
     */
    public static void main(String[] args) {
        ThreadMXBeanTestBase test = new DoubleAllocationTest();
        Monitoring.runTest(test, test.setGarbageProducer(args));
    }
}
