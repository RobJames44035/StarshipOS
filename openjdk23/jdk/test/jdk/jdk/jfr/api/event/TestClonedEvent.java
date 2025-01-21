/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.event;

import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;
import jdk.jfr.Registered;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Tests that a cloned event can be successfully committed.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.event.TestClonedEvent
 */

public class TestClonedEvent  {

    public static void main(String[] args) throws Throwable {
        Recording r = new Recording();
        r.enable(MyEvent.class);

        r.start();

        MyEvent event = new MyEvent();

        MyEvent event2 = (MyEvent)event.clone();

        FlightRecorder.register(MyEvent.class);
        event.commit();
        event2.commit();

        r.stop();

        List<RecordedEvent> events = Events.fromRecording(r);
        Asserts.assertEquals(2, events.size());

        r.close();

        FlightRecorder.unregister(MyEvent.class);

        Recording r2 = new Recording();
        r2.enable(MyEvent.class);

        r2.start();
        event.commit();
        event2.commit();

        r2.stop();

        events = Events.fromRecording(r2);
        Asserts.assertEquals(0, events.size());

        r2.close();
    }

    @Registered(false)
    private static class MyEvent extends Event implements Cloneable {

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

    }

}
