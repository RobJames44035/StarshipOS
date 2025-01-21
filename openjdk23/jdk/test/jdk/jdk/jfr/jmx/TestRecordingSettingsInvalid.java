/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx;

import java.util.HashMap;
import java.util.Map;

import jdk.management.jfr.FlightRecorderMXBean;
import jdk.test.lib.Asserts;

/**
 * @test
 * @key jfr
 * @summary Verify exception when setting invalid settings.
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.TestRecordingSettingsInvalid
 */
public class TestRecordingSettingsInvalid {
    public static void main(String[] args) throws Exception {
        Map<String, String> settings = new HashMap<>();
        settings.put(null, "true");
        settings.put("java.exception_throw#stackTrace", null);
        settings.put("java.exception_throw#threshold", "not-a-number");
        settings.put("os.information#period", "4 x");

        // TODO: No exception for these settings. Not sure how much validation can be done on settings.
        //settings.put("java.exception_throw#enabled", "maybe");
        //settings.put("os.information#period", "-4 s");
        //settings.put("java.exception_throw#thread", "");
        //settings.put("", "true");
        //settings.put("os.information#what", "4 ms");
        //settings.put("#", "4 what");
        //settings.put("java.exception_throw#", "true");
        //settings.put("java.exception_throwenabled", "false");

        FlightRecorderMXBean bean = JmxHelper.getFlighteRecorderMXBean();

        for (String key : settings.keySet()) {
            System.out.printf("settings: %s=%s%n", key, settings.get(key));
            Map<String, String> temp = new HashMap<String, String>();
            temp.put(key, settings.get(key));
            long recId = -1;
            try {
                recId = bean.newRecording();
                bean.setRecordingSettings(recId, temp);
                bean.startRecording(recId);
                bean.stopRecording(recId);
                Asserts.fail("Missing exception");
            } catch (Exception e) {
                System.out.println("Got expected exception: " + e.getMessage());
            } finally {
                bean.closeRecording(recId);
            }
        }
    }

}
