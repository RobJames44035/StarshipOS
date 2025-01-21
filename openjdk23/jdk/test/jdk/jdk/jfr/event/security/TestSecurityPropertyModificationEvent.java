/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.event.security;

import java.security.Security;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.security.JDKSecurityProperties;

/*
 * @test
 * @bug 8148188
 * @summary Enhance the security libraries to record events of interest
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.event.security.TestSecurityPropertyModificationEvent
 */
public class TestSecurityPropertyModificationEvent {

    static List<String> keys = JDKSecurityProperties.getKeys();
    static String keyValue = "shouldBecomeAnEvent";

    public static void main(String[] args) throws Exception {

        // If events in java.base are used before JFR is initialized
        // the event handler field will be of type java.lang.Object.
        // Adding this for one of the security events makes sure
        // we have test coverage of this mode as well.
        for (String key : keys) {
            Security.setProperty(key, keyValue);
        }

        try (Recording recording = new Recording()) {
            recording.enable(EventNames.SecurityProperty).withStackTrace();
            recording.start();
            for (String key: keys) {
                Security.setProperty(key, keyValue);
            }
            recording.stop();

            List<RecordedEvent> events = Events.fromRecording(recording);
            Asserts.assertEquals(events.size(), keys.size(),
                    "Incorrect number of events");
            assertEvent(events);
        }
    }

    private static void assertEvent(List<RecordedEvent> events) throws Exception {
        int i = 1;
        for (RecordedEvent e : events) {
            if (keys.contains(e.getString("key"))) {
                Events.assertField(e, "value").equal(keyValue);
                i++;
                Events.assertTopFrame(e, TestSecurityPropertyModificationEvent.class, "main");
            } else {
                System.out.println(events);
                throw new Exception("Unexpected event at index:" + i);
            }
        }
    }
}
