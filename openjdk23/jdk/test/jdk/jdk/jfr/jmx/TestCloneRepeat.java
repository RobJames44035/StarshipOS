/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import jdk.management.jfr.FlightRecorderMXBean;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventField;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.SimpleEventHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.TestCloneRepeat
 */
public class TestCloneRepeat {
    public static void main(String[] args) throws Exception {
        FlightRecorderMXBean bean = JmxHelper.getFlighteRecorderMXBean();

        long orgId = bean.newRecording();
        bean.startRecording(orgId);

        List<Integer> ids = new ArrayList<>();
        for (int i=0; i<5; i++) {
            long cloneId = bean.cloneRecording(orgId, false);
            SimpleEventHelper.createEvent(i);
            bean.stopRecording(cloneId);
            Path path = Paths.get(".", i + "-org.jfr");
            bean.copyTo(cloneId, path.toString());
            bean.closeRecording(cloneId);
            ids.add(i);
            verifyEvents(path, ids);
        }

        bean.closeRecording(orgId);
    }

    private static void verifyEvents(Path path, List<Integer> ids) throws Exception {
        List<RecordedEvent> events = RecordingFile.readAllEvents(path);
        Iterator<RecordedEvent> iterator = events.iterator();
        for (int i=0; i<ids.size(); i++) {
            Asserts.assertTrue(iterator.hasNext(), "Missing event " + ids.get(i));
            EventField idField = Events.assertField(iterator.next(), "id");
            System.out.println("Event.id=" + idField.getValue());
            idField.equal(ids.get(i).intValue());
        }
        if (iterator.hasNext()) {
            Asserts.fail("Got extra event: " + iterator.next());
        }
    }
}
