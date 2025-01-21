/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.jfr.event.diagnostics;

import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @modules java.management
 * @run main/othervm jdk.jfr.event.diagnostics.TestHeapDump
 */
public class TestHeapDump {
    private final static String EVENT_NAME = EventNames.HeapDump;

    public static void main(String[] args) throws Exception {

        Path path = Paths.get("dump.hprof").toAbsolutePath();
        try (Recording r = new Recording()) {
            r.enable(EVENT_NAME);
            r.start();
            heapDump(path);
            r.stop();
            List<RecordedEvent> events = Events.fromRecording(r);
            if (events.size() != 1) {
                throw new Exception("Expected one event, got " + events.size());
            }
            RecordedEvent e = events.getFirst();
            Events.assertField(e, "destination").equal(path.toString());
            Events.assertField(e, "gcBeforeDump").equal(true);
            Events.assertField(e, "onOutOfMemoryError").equal(false);
            Events.assertField(e, "size").equal(Files.size(path));
            Events.assertField(e, "compression").below(1);
            Events.assertField(e, "overwrite").equal(false);
            System.out.println(e);
        }
    }

    private static void heapDump(Path path) throws Exception {
        ObjectName objectName = new ObjectName("com.sun.management:type=HotSpotDiagnostic");
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        Object[] parameters = new Object[2];
        parameters[0] = path.toString();
        parameters[1] = true;
        String[] signature = new String[] { String.class.getName(), boolean.class.toString() };
        mbeanServer.invoke(objectName, "dumpHeap", parameters, signature);
    }
}
