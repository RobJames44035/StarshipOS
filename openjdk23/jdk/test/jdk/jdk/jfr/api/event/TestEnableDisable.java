/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Test enable/disable event and verify recording has expected events.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.event.TestEnableDisable
 */

public class TestEnableDisable {

    public static void main(String[] args) throws Exception {
        List<MyEvent> expectedEvents = new ArrayList<>();
        Recording r = new Recording();

        r.start();
        createEvent(expectedEvents, true); // id=0 Custom event classes are enabled by default.

        r.disable(MyEvent.class);
        createEvent(expectedEvents, false); // id=1
        r.enable(MyEvent.class);
        createEvent(expectedEvents, true);  // id=2

        // enable/disable by event setting name
        String eventSettingName = String.valueOf(EventType.getEventType(MyEvent.class).getId());
        System.out.println("eventSettingName=" + eventSettingName);

        r.disable(eventSettingName);
        createEvent(expectedEvents, false); // id=3
        r.enable(eventSettingName);
        createEvent(expectedEvents, true);

        r.stop();
        createEvent(expectedEvents, false);

        Iterator<MyEvent> expectedIterator = expectedEvents.iterator();
        for (RecordedEvent event : Events.fromRecording(r)) {
            System.out.println("event.id=" + Events.assertField(event, "id").getValue());
            Asserts.assertTrue(expectedIterator.hasNext(), "Found more events than expected");
            Events.assertField(event, "id").equal(expectedIterator.next().id);
        }
        Asserts.assertFalse(expectedIterator.hasNext(), "Did not find all expected events.");

        r.close();
    }

    private static int eventId = 0;
    private static void createEvent(List<MyEvent> expectedEvents, boolean isExpected) {
        MyEvent event = new MyEvent();
        event.begin();
        event.id = eventId;
        event.commit();

        if (isExpected) {
            expectedEvents.add(event);
        }
        eventId++;
    }

    private static class MyEvent extends Event {
        private int id;
    }

}
