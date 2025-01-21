/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.event.oldobject;

import java.util.ArrayList;
import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedClass;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedObject;
import jdk.jfr.internal.test.WhiteBox;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules jdk.jfr/jdk.jfr.internal.test
 * @run main/othervm  -XX:TLABSize=2k jdk.jfr.event.oldobject.TestAllocationTime
 */
public class TestAllocationTime {

    private static class BeforeLeakEvent extends Event {
    }
    private static class AfterLeakEvent extends Event {
    }
    private static class Leak {
    }

    public static List<Leak[]> leak = new ArrayList<>(OldObjects.MIN_SIZE);

    public static void main(String[] args) throws Exception {
        WhiteBox.setWriteAllObjectSamples(true);

        while(true) {
            try (Recording recording = new Recording()) {
                leak.clear();
                recording.enable(EventNames.OldObjectSample).withStackTrace().with("cutoff", "1 h");
                recording.start();

                BeforeLeakEvent be = new BeforeLeakEvent();
                be.commit();

                // Allocate array to trigger sampling code path for interpreter / c1
                for (int i = 0; i < OldObjects.MIN_SIZE; i++) {
                    leak.add(new Leak[0]);
                }

                AfterLeakEvent ae = new AfterLeakEvent();
                ae.commit();

                recording.stop();

                List<RecordedEvent> events = Events.fromRecording(recording);
                Events.hasEvents(events);
                RecordedObject sample = findLeak(events, BeforeLeakEvent.class.getName());
                if (sample != null)  {
                    long beforeTime = find(events, BeforeLeakEvent.class.getName()).getValue("startTime");
                    long allocationTime = sample.getValue("allocationTime");
                    long afterTime = find(events, AfterLeakEvent.class.getName()).getValue("startTime");
                    System.out.println("Before time     : " + beforeTime);
                    System.out.println("Allocation time : " + allocationTime);
                    System.out.println("After time      : " + afterTime);

                    if (allocationTime < beforeTime) {
                        throw new Exception("Allocation should not happen this early");
                    }
                    if (allocationTime > afterTime) {
                        throw new Exception("Allocation should not happen this late");
                    }
                    return; // sample ok
                }
            }
        }
    }

    private static RecordedObject findLeak(List<RecordedEvent> events, String name) throws Exception {
        for (RecordedEvent e : events) {
            if (e.getEventType().getName().equals(EventNames.OldObjectSample)) {
                RecordedObject object = e.getValue("object");
                RecordedClass rc = object.getValue("type");
                if (rc.getName().equals(Leak[].class.getName())) {
                    return e;
                }
            }
        }
        return null;
    }

    private static RecordedEvent find(List<RecordedEvent> events, String name) throws Exception {
        for (RecordedEvent e : events) {
            if (e.getEventType().getName().equals(name)) {
                return e;
            }
        }
        throw new Exception("Could not find event with name " + name);
    }
}
