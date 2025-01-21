/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx;

import java.util.List;

import jdk.management.jfr.FlightRecorderMXBean;
import jdk.management.jfr.RecordingInfo;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm -Djdk.attach.allowAttachSelf=true -Dcom.sun.management.jmxremote jdk.jfr.jmx.TestGetRecordings
 */
public class TestGetRecordings {
    public static void main(String[] args) throws Throwable {
        FlightRecorderMXBean bean =JmxHelper.getFlighteRecorderMXBean();

        List<RecordingInfo> preCreateRecordings = bean.getRecordings();
        long recId = bean.newRecording();
        JmxHelper.verifyNotExists(recId, preCreateRecordings);
        bean.closeRecording(recId);
        JmxHelper.verifyNotExists(recId, bean.getRecordings());

        long selfPID = JmxHelper.getPID();
        FlightRecorderMXBean remoteBean = JmxHelper.getFlighteRecorderMXBean(selfPID);
        long remoteRecId = remoteBean.newRecording();
        remoteBean.getRecordings();
        remoteBean.closeRecording(remoteRecId);
    }
}
