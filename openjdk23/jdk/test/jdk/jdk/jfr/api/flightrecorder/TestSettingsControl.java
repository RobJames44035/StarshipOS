/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.flightrecorder;

import static jdk.test.lib.Asserts.assertTrue;

import java.util.Set;

import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.SettingControl;
import jdk.jfr.SettingDefinition;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.flightrecorder.TestSettingsControl
 */
public class TestSettingsControl {
    static class MySettingsControl extends SettingControl {

        public static boolean setWasCalled;

        private String value = "default";

        @Override
        public String combine(Set<String> values) {
           StringBuilder sb = new StringBuilder();
            for(String s : values) {
                sb.append(s).append(" ");
            }
            return sb.toString();
        }

        @Override
        public void setValue(String value) {
            setWasCalled = true;
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

    }

    static class MyCustomSettingEvent extends Event {
        @SettingDefinition
        boolean mySetting(MySettingsControl msc) {
            return true;
        }
    }

    public static void main(String[] args) throws Throwable {
        Recording r = new Recording();
        r.enable(MyCustomSettingEvent.class).with("mySetting", "myvalue");
        r.start();
        MyCustomSettingEvent e = new MyCustomSettingEvent();
        e.commit();
        r.stop();
        r.close();
        assertTrue(MySettingsControl.setWasCalled, "SettingControl.setValue was not called");
    }
}




