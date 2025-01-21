/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package nsk.monitoring.ThreadMXBean.GetThreadCpuTime;

import nsk.share.*;
import nsk.monitoring.share.*;
import nsk.monitoring.ThreadMXBean.ThreadMXBeanTestBase;


/**
 * Tests  getThreadCpuTime(long[] ids) and getThreadUserTime(long[] ids)
 * functions of com.sun.management.ThreadMXBean
 * <p>
 *  - any method called with null argument should throw NullPointerException
 * for direct and proxy MBean delivery methods and RuntimeException for
 * server MBean delivery method (MBeanServer)
 * <br>
 *  - any method called with zero (0 for long, { 0 } for long[]) argument
 * should throw IllegalArgumentException for direct and proxy MBean delivery
 * methods and RuntimeException for server MBean delivery method (MBeanServer)
 */
public class IllegalArgumentsTest extends ThreadMXBeanTestBase {

    /**
     * Actually runs the test
     */
    public void run() {
        if (threadMXBean == null)
            return;
        int exceptions = 0;
        // getThreadCpuTime(long[]) with null
        try {
            threadMXBean.getThreadCpuTime(null);
        } catch (NullPointerException e) {
            log.info("Caught expected NPE : " + e.getMessage());
            exceptions++;
        } catch (RuntimeException e1) {
            log.info("Caught expected RuntimeException : " + e1.getMessage());
            exceptions++;
        }
        // getThreadCpuTime(long[]) with { 0 }
        try {
            threadMXBean.getThreadCpuTime(new long[] { 0 });
        } catch (IllegalArgumentException e) {
            log.info("Caught expected IllegalArgumentException : " + e.getMessage());
            exceptions++;
        } catch (RuntimeException e1) {
            log.info("Caught expected RuntimeException : " + e1.getMessage());
            exceptions++;
        }
        // getThreadUserTime(long[]) with null
        try {
            threadMXBean.getThreadUserTime(null);
        } catch (NullPointerException e) {
            log.info("Caught expected NPE : " + e.getMessage());
            exceptions++;
        } catch (RuntimeException e1) {
            log.info("Caught expected RuntimeException : " + e1.getMessage());
            exceptions++;
        }
        // getThreadCpuTime(long[]) with { 0 }
        try {
            threadMXBean.getThreadUserTime(new long[] { 0 });
        } catch (IllegalArgumentException e) {
            log.info("Caught expected IllegalArgumentException : " + e.getMessage());
            exceptions++;
        } catch (RuntimeException e1) {
            log.info("Caught expected RuntimeException : " + e1.getMessage());
            exceptions++;
        }
        // 4 exceptions should have been caught
        if (exceptions != 4)
            throw new TestFailure("Failure! Expected to catch 4 exceptions, "
                    + "actually caught : " + exceptions);
        log.info("IllegalArgumentsTest passed.");
    }

    /**
     * Entry point for java program
     * @param args sets the test configuration
     */
    public static void main(String[] args) {
        Monitoring.runTest(new IllegalArgumentsTest(), args);
    }
}
