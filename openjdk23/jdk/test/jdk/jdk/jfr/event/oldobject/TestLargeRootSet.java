/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.jfr.event.oldobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedClass;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedFrame;
import jdk.jfr.consumer.RecordedMethod;
import jdk.jfr.consumer.RecordedObject;
import jdk.jfr.consumer.RecordedStackTrace;
import jdk.jfr.internal.test.WhiteBox;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules jdk.jfr/jdk.jfr.internal.test
 * @run main/othervm -XX:TLABSize=2k jdk.jfr.event.oldobject.TestLargeRootSet
 */
public class TestLargeRootSet {
    static class Node {
        Node left;
        Node right;
        Object value;
    }

    static class Leak {
        // Leaking object has to be of some size,
        // otherwise Node object wins most of the
        // slots in the object queue.
        // In a normal application, objects would
        // be of various size and allocated over a
        // longer period of time. This would create
        // randomness not present in the test.
        public long value1;
        public Object value2;
        float value3;
        int value4;
        double value5;
    }

    public static void main(String[] args) throws Exception {
        WhiteBox.setWriteAllObjectSamples(true);
        WhiteBox.setSkipBFS(true);
        HashMap<Object, Node> leaks = new HashMap<>();
        try (Recording r = new Recording()) {
            r.enable(EventNames.OldObjectSample).withStackTrace().with("cutoff", "infinity");
            r.start();
            for (int i = 0; i < 1_000_000; i++) {
                Node node = new Node();
                node.left = new Node();
                node.right = new Node();
                node.right.value = new Leak();
                leaks.put(i, node);
            }
            r.stop();
            List<RecordedEvent> events = Events.fromRecording(r);
            Events.hasEvents(events);
            for (RecordedEvent e : events) {
                RecordedClass type = e.getValue("object.type");
                if (type.getName().equals(Leak.class.getName())) {
                    return;
                }
            }
        }
    }
}
