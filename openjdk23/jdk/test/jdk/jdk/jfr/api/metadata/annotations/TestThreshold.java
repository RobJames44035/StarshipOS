/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.jfr.api.metadata.annotations;

import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Threshold;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.annotations.TestThreshold
 */
public class TestThreshold {

    @Threshold("23 s")
    static class PeriodicEvent extends Event {
    }

    public static void main(String[] args) throws Exception {
        EventType thresholdEvent = EventType.getEventType(PeriodicEvent.class);
        String defaultValue = Events.getSetting(thresholdEvent,Threshold.NAME).getDefaultValue();
        Asserts.assertEquals(defaultValue, "23 s", "@Threshold(\"23 s\") Should result in threshold '23 s'");
    }
}
