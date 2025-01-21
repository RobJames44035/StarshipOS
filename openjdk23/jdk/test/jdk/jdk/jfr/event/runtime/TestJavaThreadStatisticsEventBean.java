/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import static jdk.test.lib.Asserts.assertTrue;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 *
 * @run main/othervm jdk.jfr.event.runtime.TestJavaThreadStatisticsEventBean
 */
public class TestJavaThreadStatisticsEventBean {
    private final static String EVENT_NAME = EventNames.JavaThreadStatistics;

    // Compare JFR thread counts to ThreadMXBean counts
    public static void main(String[] args) throws Throwable {
        long mxDaemonCount = -1;
        long mxActiveCount = -1;

        // Loop until we are sure no threads were started during the recording.
        Recording recording = null;
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        long totalCountBefore = -1;
        while (totalCountBefore != mxBean.getTotalStartedThreadCount()) {
            recording = new Recording();
            recording.enable(EVENT_NAME).withThreshold(Duration.ofMillis(10));
            totalCountBefore = mxBean.getTotalStartedThreadCount();
            recording.start();
            mxDaemonCount = mxBean.getDaemonThreadCount();
            mxActiveCount = mxBean.getThreadCount();
            recording.stop();
            final String msg = "testCountByMXBean: threadsBefore=%d, threadsAfter=%d%n";
            System.out.format(msg, totalCountBefore, mxBean.getTotalStartedThreadCount());
        }

        List<RecordedEvent> events= Events.fromRecording(recording);
        boolean isAnyFound = false;
        for (RecordedEvent event : events) {
            System.out.println("Event:" + event);
            isAnyFound = true;
            Events.assertField(event, "daemonCount").equal(mxDaemonCount);
            Events.assertField(event, "activeCount").equal(mxActiveCount);
            Events.assertField(event, "accumulatedCount").atLeast(mxActiveCount);
            Events.assertField(event, "peakCount").atLeast(mxActiveCount);
        }
        assertTrue(isAnyFound, "Correct event not found");
    }

}
