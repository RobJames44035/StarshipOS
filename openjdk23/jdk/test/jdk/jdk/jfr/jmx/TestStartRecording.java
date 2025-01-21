/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx;

import java.util.List;
import java.util.Map;

import jdk.management.jfr.ConfigurationInfo;
import jdk.management.jfr.EventTypeInfo;
import jdk.management.jfr.FlightRecorderMXBean;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.TestStartRecording
 */
public class TestStartRecording {
    public static void main(String[] args) throws Throwable {
        FlightRecorderMXBean bean = JmxHelper.getFlighteRecorderMXBean();
        long recId = bean.newRecording();
        bean.startRecording(recId);

        // TODO: Remove debug logs
        List<ConfigurationInfo> configs = bean.getConfigurations();
        for (ConfigurationInfo config : configs) {
            System.out.println("config=" + config.toString());
        }
        Map<String, String> settings = bean.getRecordingSettings(recId);
        for (String key : settings.keySet()) {
            System.out.println("setting: " + key + "=" + settings.get(key));
        }
        List<EventTypeInfo> types = bean.getEventTypes();
        for (EventTypeInfo type : types) {
            System.out.println("type=" + type.getName());
        }
        //////////////////////

        bean.stopRecording(recId);
        bean.closeRecording(recId);
    }
}
