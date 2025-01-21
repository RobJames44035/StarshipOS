/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.gc == "Serial" | vm.gc == null
 * @requires vm.hasJFR
 * @library /test/lib
 * @run driver jdk.jfr.event.runtime.TestVMInfoEvent generateFlagsFile
 * @run main/othervm -XX:Flags=TestVMInfoEvent.flags -Xmx500m jdk.jfr.event.runtime.TestVMInfoEvent arg1 arg2
 */
public class TestVMInfoEvent {
    private final static String EVENT_NAME = EventNames.JVMInformation;

    public static void main(String[] args) throws Exception {
        if( (args.length > 0) && ("generateFlagsFile".equals(args[0])) ) {
            generateFlagsFile();
            return;
        }
        RuntimeMXBean mbean = ManagementFactory.getRuntimeMXBean();
        Recording recording = new Recording();
        recording.enable(EVENT_NAME);
        recording.start();
        recording.stop();

        List<RecordedEvent> events = Events.fromRecording(recording);
        Events.hasEvents(events);
        for (RecordedEvent event : events) {
            System.out.println("Event:" + event);
            Events.assertField(event, "jvmName").equal(mbean.getVmName());
            String jvmVersion = Events.assertField(event, "jvmVersion").notEmpty().getValue();
            if (!jvmVersion.contains(mbean.getVmVersion())) {
                Asserts.fail(String.format("%s does not contain %s", jvmVersion, mbean.getVmVersion()));
            }

            String jvmArgs = Events.assertField(event, "jvmArguments").notNull().getValue();
            String jvmFlags = Events.assertField(event, "jvmFlags").notNull().getValue();
            Long pid = Events.assertField(event, "pid").atLeast(0L).getValue();
            Asserts.assertEquals(pid, ProcessHandle.current().pid());
            String eventArgs = (jvmFlags.trim() + " " + jvmArgs).trim();
            String beanArgs = mbean.getInputArguments().stream().collect(Collectors.joining(" "));
            Asserts.assertEquals(eventArgs, beanArgs, "Wrong inputArgs");

            final String javaCommand = mbean.getSystemProperties().get("sun.java.command");
            Events.assertField(event, "javaArguments").equal(javaCommand);
            Events.assertField(event, "jvmStartTime").equal(mbean.getStartTime());
        }
    }

    public static void generateFlagsFile() throws Exception {
        Files.writeString(Paths.get("", "TestVMInfoEvent.flags"), "+UseSerialGC");
    }
}
