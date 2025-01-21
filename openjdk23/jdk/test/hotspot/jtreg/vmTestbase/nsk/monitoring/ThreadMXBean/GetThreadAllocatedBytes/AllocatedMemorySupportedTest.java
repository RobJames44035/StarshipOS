/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package nsk.monitoring.ThreadMXBean.GetThreadAllocatedBytes;

import nsk.share.*;
import nsk.monitoring.share.*;
import nsk.monitoring.ThreadMXBean.ThreadMXBeanTestBase;

/**
 * Tests isThreadAllocatedMemorySupported(), isThreadAllocatedMemoryEnabled()
 * and setThreadAllocatedMemoryEnabled(boolean enabled) functions
 * of com.sun.management.ThreadMXBean
 * <p>
 *  - isThreadAllocatedMemorySupported() should return true
 * <br>
 *  - isThreadAllocatedMemoryEnabled() should return true by default
 * <br>
 *  - isThreadAllocatedMemoryEnabled() should return false after
 * setThreadAllocatedMemoryEnabled(false) call
 * <br>
 *  - isThreadAllocatedMemoryEnabled() should return true again after
 * setThreadAllocatedMemoryEnabled(true) call
 */
public class AllocatedMemorySupportedTest extends ThreadMXBeanTestBase {

    /**
     * Actually runs the test
     */
    public void run() {
        if (threadMXBean == null)
            return;
        // Check that isThreadAllocatedMemorySupported() returns true by default
        if (! threadMXBean.isThreadAllocatedMemorySupported()) {
            throw new TestFailure("Failure! isThreadAllocatedMemorySupported() "
                   + "does not return true by default...");
        }
        // Check that isThreadAllocatedMemoryEnabled() returns true by default
        if (! threadMXBean.isThreadAllocatedMemoryEnabled()) {
            throw new TestFailure("Failure! isThreadAllocatedMemoryEnabled() "
                    + "does not return true by default...");
        }
        // Call setThreadAllocatedMemoryEnabled(false)
        threadMXBean.setThreadAllocatedMemoryEnabled(false);
        // Check that isThreadAllocatedMemoryEnabled() now returns false
        if (threadMXBean.isThreadAllocatedMemoryEnabled()) {
            throw new TestFailure("Failure! setThreadAllocatedMemoryEnabled(false) "
                    + "does not operate as expected...");
        }
        // Call setThreadAllocatedMemoryEnabled(true)
        threadMXBean.setThreadAllocatedMemoryEnabled(true);
        // Check that isThreadAllocatedMemoryEnabled() returns true again
        if (! threadMXBean.isThreadAllocatedMemoryEnabled()) {
            throw new TestFailure("Failure! setThreadAllocatedMemoryEnabled(true) "
                    + "does not operate as expected...");
        }
        log.info("AllocatedMemorySupportedTest passed.");
    }

    /**
     * Entry point for java program
     * @param args sets the test configuration
     */
    public static void main(String[] args) {
        Monitoring.runTest(new AllocatedMemorySupportedTest(), args);
    }
}
