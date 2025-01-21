/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
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
 * @requires vm.hasJFR & vm.gc.Shenandoah
 * @summary Test leak profiler with Shenandoah
 * @library /test/lib /test/jdk
 * @modules jdk.jfr/jdk.jfr.internal.test
 * @run main/othervm  -XX:TLABSize=2k -XX:+UseShenandoahGC jdk.jfr.event.oldobject.TestShenandoah
 */
public class TestShenandoah {

    static private class FindMe {
    }

    public static List<FindMe[]> list = new ArrayList<>(OldObjects.MIN_SIZE);

    public static void main(String[] args) throws Exception {
        WhiteBox.setWriteAllObjectSamples(true);

        while (true) {
            try (Recording r = new Recording()) {
                r.enable(EventNames.OldObjectSample).withStackTrace().with("cutoff", "infinity");
                r.start();
                allocateFindMe();
                System.gc();
                r.stop();
                List<RecordedEvent> events = Events.fromRecording(r);
                System.out.println(events);
                if (OldObjects.countMatchingEvents(events, FindMe[].class, null, null, -1, "allocateFindMe") > 0) {
                    return;
                }
                System.out.println("Could not find leaking object, retrying...");
            }
            list.clear();
        }
    }

    public static void allocateFindMe() {
        for (int i = 0; i < OldObjects.MIN_SIZE; i++) {
            // Allocate array to trigger sampling code path for interpreter / c1
            list.add(new FindMe[0]);
        }
    }

}
