/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.metadata.eventtype;

import java.util.List;

import jdk.jfr.EventType;
import jdk.jfr.SettingDescriptor;
import jdk.test.lib.Asserts;

/**
 * @test
 * @summary Test getSettings()
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.api.metadata.eventtype.TestGetSettings
 */
public class TestGetSettings {

    public static void main(String[] args) throws Throwable {
        EventType eventType = EventType.getEventType(EventWithCustomSettings.class);
        List<SettingDescriptor> settings = eventType.getSettingDescriptors();
        Asserts.assertEquals(settings.size(), 5, "Wrong number of settings");

        // test immutability
        try {
            settings.add(settings.getFirst());
            Asserts.fail("Should not be able to modify list returned by getSettings()");
        } catch (UnsupportedOperationException uoe) {
            // OK, as expected
        }
    }
}
