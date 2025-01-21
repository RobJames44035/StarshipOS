/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx;

import java.util.List;

import jdk.jfr.RecordingState;
import jdk.management.jfr.FlightRecorderMXBean;
import jdk.management.jfr.RecordingInfo;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.TestMultipleRecordings
 */
public class TestMultipleRecordings {
    public static void main(String[] args) throws Throwable {
        FlightRecorderMXBean bean = JmxHelper.getFlighteRecorderMXBean();
        System.out.println("bean.class=" + bean.getClass().getName());

        // Start recA
        long recIdA = createRecording(bean);
        startRecording(recIdA, bean);

        // Start recB
        long recIdB = createRecording(bean);
        startRecording(recIdB, bean);

        // Stop and destroy recA
        stopRecording(recIdA, bean);
        destroyRecording(recIdA, bean);

        // Start, stop and destroy recC
        long recIdC = createRecording(bean);
        startRecording(recIdC, bean);
        stopRecording(recIdC, bean);
        destroyRecording(recIdC, bean);

        // Stop and destroy recB
        stopRecording(recIdB, bean);
        destroyRecording(recIdB, bean);
    }

    private static long createRecording(FlightRecorderMXBean bean) throws Exception {
        List<RecordingInfo> preCreateRecordings = bean.getRecordings();
        long recId = bean.newRecording();
        JmxHelper.verifyNotExists(recId, preCreateRecordings);
        JmxHelper.verifyState(recId, RecordingState.NEW, bean);
        return recId;
    }

    private static void startRecording(long recId, FlightRecorderMXBean bean) throws Exception {
        JmxHelper.verifyState(recId, RecordingState.NEW, bean);
        bean.startRecording(recId);
        JmxHelper.verifyState(recId, RecordingState.RUNNING, bean);
    }

    private static void stopRecording(long recId, FlightRecorderMXBean bean) throws Exception {
        JmxHelper.verifyState(recId, RecordingState.RUNNING, bean);
        bean.stopRecording(recId);
        JmxHelper.verifyState(recId, RecordingState.STOPPED, bean);
    }

    private static void destroyRecording(long recId, FlightRecorderMXBean bean) throws Exception {
        JmxHelper.verifyState(recId, RecordingState.STOPPED, bean);
        bean.closeRecording(recId);
        JmxHelper.verifyNotExists(recId, bean.getRecordings());
    }

}
