/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.jfr.api.event;

import java.time.Duration;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.SimpleEvent;

/**
 * @test
 * @summary Test for RecordedEvent.getDuration()
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.event.TestBeginEnd
 */
public class TestBeginEnd {

    public static void main(String[] args) throws Exception {

        Recording r = new Recording();
        r.enable(SimpleEvent.class);
        r.start();

        // Test enabled - single commit
        SimpleEvent e1 = new SimpleEvent();
        e1.id = 1; // should be included
        e1.commit();

        // Test enabled - begin - commit
        SimpleEvent e2 = new SimpleEvent();
        e2.begin();
        e2.id = 2; // should be included
        e2.commit();

        // Test enabled - begin - end - commit
        SimpleEvent e3 = new SimpleEvent();
        e3.begin();
        e3.id = 3; // should be included
        e3.end();
        e3.commit();

        // Test enabled - end - commit
        SimpleEvent e4 = new SimpleEvent();
        e4.id = 4; // should be included
        e4.end();
        e4.commit();

        // Test half enabled - begin - commit
        r.disable(SimpleEvent.class);
        SimpleEvent e5 = new SimpleEvent();
        e5.begin();
        r.enable(SimpleEvent.class);
        e5.id = 5; // should be included
        e5.commit();

        // Test half enabled - begin - end - commit
        r.disable(SimpleEvent.class);
        SimpleEvent r6 = new SimpleEvent();
        r6.begin();
        r.enable(SimpleEvent.class);
        r6.id = 6; // should be included
        r6.end();
        r6.commit();

        // Test half enabled - begin - commit with high threshold
        r.disable(SimpleEvent.class);
        SimpleEvent r7 = new SimpleEvent();
        r7.begin();
        r.enable(SimpleEvent.class).withThreshold(Duration.ofDays(1));
        r7.id = 7; // should not be included
        r7.commit();
        r.stop();

        List<RecordedEvent> recordedEvents = Events.fromRecording(r);
        for (RecordedEvent re : recordedEvents) {
           Integer id =  re.getValue("id");
           System.out.println("Found if " + id);
           if (id < 1 || id > 6) {
               throw new Exception("Unexpected id found " + id);
           }
        }
        if (recordedEvents.size() != 6) {
            throw new Exception("Expected 6 events");
        }
    }

}
