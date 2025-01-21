/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.event.oldobject;

import java.util.ArrayList;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.internal.test.WhiteBox;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules jdk.jfr/jdk.jfr.internal.test
 * @run main/othervm -XX:TLABSize=2k jdk.jfr.event.oldobject.TestListenerLeak
 */
public class TestListenerLeak {

    private interface TestListener {
        void onListen();
    }

    static class Stuff {
    }

    static class ListenerThread extends Thread {

        private List<Stuff[]> stuff;

        public ListenerThread(List<Stuff[]> stuff) {
            this.stuff = stuff;
        }

        public void run() {
            listener.add(new TestListener() {
                @Override
                public void onListen() {
                    System.out.println(stuff);
                }
            });
        }
    }

    private static List<TestListener> listener = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        WhiteBox.setWriteAllObjectSamples(true);
        while (true) {
            try (Recording r = new Recording()) {
                r.enable(EventNames.OldObjectSample).withStackTrace().with("cutoff", "infinity");
                r.start();
                listenerLeak();
                r.stop();
                List<RecordedEvent> events = Events.fromRecording(r);
                if (OldObjects.countMatchingEvents(events, Stuff[].class, null, null, -1, "listenerLeak") != 0) {
                    return; // Success
                }
                System.out.println("Could not find leak with " + Stuff[].class + ". Retrying.");
            }
        }
    }

    private static void listenerLeak() throws InterruptedException {
        List<Stuff[]> stuff = new ArrayList<>(OldObjects.MIN_SIZE);
        for (int i = 0; i < OldObjects.MIN_SIZE; i++) {
            // Allocate array to trigger sampling code path for interpreter / c1
            stuff.add(new Stuff[0]);
        }

        ListenerThread t = new ListenerThread(stuff);
        t.start();
        t.join();
    }

}
