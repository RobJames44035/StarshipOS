/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package nsk.monitoring.ThreadMXBean.GetThreadCpuTime;

import nsk.share.*;
import nsk.monitoring.share.*;
import nsk.monitoring.ThreadMXBean.*;

/**
 * Tests getThreadCpuTime(long[] ids) and getThreadUserTime(long[] ids)
 * functions of com.sun.management.ThreadMXBean
 * <p>
 * All methods should
 * <br>
 * -  return -1 if ThreadCpuTime allocation is
 * not enabled
 * <br>
 * - return -1 for not started thread
 * <br>
 * - return > 0 value for any running thread
 * <br>
 * - return -1 for finished thread
 */
public class BaseBehaviorTest extends ThreadMXBeanTestBase {

    /**
     * Actually runs the test
     */
    public void run() {
        if (threadMXBean == null)
            return;
        MXBeanTestThread thread = new MXBeanTestThread();
        long id = thread.getId();
        long[] idArr = new long[] { id };
        long[] resultArr;
        // Expect -1 for not started threads
        resultArr = threadMXBean.getThreadCpuTime(idArr);
        if (resultArr[0] != -1)
            throw new TestFailure("Failure! getThreadCpuTime(long[] ids) should "
                    + "return -1 for not started threads. Recieved : " + resultArr[0]);
        resultArr = threadMXBean.getThreadUserTime(idArr);
        if (resultArr[0] != -1)
            throw new TestFailure("Failure! getThreadUserTime(long[] ids) should "
                    + "return -1 for not started threads. Recieved : " + resultArr[0]);
        BarrierHandler handler = startThreads(thread);
        try {
            handler.proceed();
            // Expect -1 for running thread if ThreadAllocatedMemory (CpuTime) is disabled
            threadMXBean.setThreadCpuTimeEnabled(false);
            resultArr = threadMXBean.getThreadCpuTime(idArr);
            if (resultArr[0] != -1)
                throw new TestFailure("Failure! getThreadCpuTime(long[] ids) should "
                    + "return -1 if threadCpuTimeEnabled is set to false. "
                    + "Recieved : " + resultArr[0]);
            resultArr = threadMXBean.getThreadUserTime(idArr);
            if (resultArr[0] != -1)
                throw new TestFailure("Failure! getThreadUserTime(long[] ids) should "
                    + "return -1 if threadCpuTimeEnabled is set to false. "
                    + "Recieved : " + resultArr[0]);
            threadMXBean.setThreadCpuTimeEnabled(true);
            // Expect > 0 value for running threads
            resultArr = threadMXBean.getThreadCpuTime(idArr);
            if (resultArr[0] < 0)
                throw new TestFailure("Failure! getThreadCpuTime(long[] ids) should "
                    + "return > 0 value for RUNNING thread. Recieved : " + resultArr[0]);
            resultArr = threadMXBean.getThreadUserTime(idArr);
            if (resultArr[0] < 0)
                throw new TestFailure("Failure! getThreadUserTime(long[] ids) should "
                    + "return > 0 value for RUNNING thread. Recieved : " + resultArr[0]);
        } finally {
            // Let thread finish
            handler.finish();
        }
        try {
            thread.join();
        } catch (InterruptedException e) {}
        // Expect -1 for finished thread
        resultArr = threadMXBean.getThreadCpuTime(idArr);
        if (resultArr[0] != -1)
            throw new TestFailure("Failure! getThreadCpuTime(long[] ids) should "
                    + "return -1 for finished threads. Recieved : " + resultArr[0]);
        resultArr = threadMXBean.getThreadUserTime(idArr);
        if (resultArr[0] != -1)
            throw new TestFailure("Failure! getThreadUserTime(long[] ids) should "
                    + "return -1 for finished threads. Recieved : " + resultArr[0]);
        log.info("BaseBehaviorTest passed.");
    }

    /**
     * Entry point for java program
     * @param args sets the test configuration
     */
    public static void main(String[] args) {
        ThreadMXBeanTestBase test = new BaseBehaviorTest();
        Monitoring.runTest(test, test.setGarbageProducer(args));
    }
}
