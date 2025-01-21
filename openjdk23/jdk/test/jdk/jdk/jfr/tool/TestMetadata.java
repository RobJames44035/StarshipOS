/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.tool;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Name;
import jdk.jfr.Registered;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary Test jfr info
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.tool.TestMetadata
 */
public class TestMetadata {

    public static void main(String[] args) throws Throwable {
        testUnfiltered();
        testIllegalOption();
        testNumberOfEventTypes();

        FlightRecorder.register(MyEvent1.class);
        FlightRecorder.register(MyEvent2.class);
        FlightRecorder.register(MyEvent3.class);
        String file = ExecuteHelper.createProfilingRecording().toAbsolutePath().toAbsolutePath().toString();
        testEventFilter(file);
        testWildcard(file);
    }

    static void testUnfiltered() throws Throwable {
        Path f = ExecuteHelper.createProfilingRecording().toAbsolutePath();
        String file = f.toAbsolutePath().toString();
        OutputAnalyzer output = ExecuteHelper.jfr("metadata");
        output.shouldContain("extends jdk.jfr.Event");

        output = ExecuteHelper.jfr("metadata", file);
        try (RecordingFile rf = new RecordingFile(f)) {
            for (EventType t : rf.readEventTypes()) {
                String name = t.getName();
                name = name.substring(name.lastIndexOf(".") + 1);
                output.shouldContain(name);
            }
        }
        Set<String> annotations = new HashSet<>();
        int lineNumber = 1;
        for (String line : output.asLines()) {
            if (line.startsWith("@")) {
                if (annotations.contains(line)) {
                    throw new Exception("Line " + lineNumber + ":" +  line + " repeats annotation");
                }
                annotations.add(line);
            } else {
                annotations.clear();
            }
            lineNumber++;
        }
    }

    static void testIllegalOption() throws Throwable {
        Path f = ExecuteHelper.createProfilingRecording().toAbsolutePath();
        String file = f.toAbsolutePath().toString();
        OutputAnalyzer output = ExecuteHelper.jfr("metadata", "--wrongOption", file);
        output.shouldContain("unknown option --wrongOption");

        output = ExecuteHelper.jfr("metadata", "--wrongOption2");
        output.shouldContain("unknown option --wrongOption2");
    }

    static void testNumberOfEventTypes() throws Throwable {
        OutputAnalyzer output = ExecuteHelper.jfr("metadata");
        int count  = 0;
        for (String line : output.asLines()) {
            if (line.contains("extends jdk.jfr.Event")) {
                count++;
            }
        }
        Asserts.assertEquals(count, FlightRecorder.getFlightRecorder().getEventTypes().size());
    }

    static void testEventFilter(String file) throws Throwable {
        OutputAnalyzer output = ExecuteHelper.jfr("metadata", "--events", "MyEvent1,MyEvent2", file);
        int count = 0;
        for (String line : output.asLines()) {
            if (line.contains("extends jdk.jfr.Event")) {
                Asserts.assertTrue(line.contains("MyEvent1") || line.contains("MyEvent2"));
                count++;
            }
        }
        Asserts.assertEQ(count, 2);

        output = ExecuteHelper.jfr("metadata", "--categories", "Customized", file);
        count = 0;
        for (String line : output.asLines()) {
            if (line.contains("extends jdk.jfr.Event")) {
                Asserts.assertTrue(line.contains("MyEvent1") || line.contains("MyEvent2") || line.contains("MyEvent3"));
                count++;
            }
        }
        Asserts.assertEQ(count, 3);
    }

    static void testWildcard(String file) throws Throwable {
        OutputAnalyzer output = ExecuteHelper.jfr("metadata", "--events", "MyEv*", file);
        int count = 0;
        for (String line : output.asLines()) {
            if (line.contains("extends jdk.jfr.Event")) {
                count++;
                Asserts.assertTrue(line.contains("MyEvent"));
            }
        }
        Asserts.assertEQ(count, 3);

        output = ExecuteHelper.jfr("metadata", "--categories", "Custo*", file);
        count = 0;
        for (String line : output.asLines()) {
            if (line.startsWith("@Category")) {
                Asserts.assertTrue(line.contains("Customized"));
            }
            if (line.contains("extends jdk.jfr.Event")) {
                count++;
                Asserts.assertTrue(line.contains("MyEvent"));
            }
        }
        Asserts.assertEQ(count, 3);
    }

    @Registered(false)
    @Category("Customized")
    @Name("MyEvent1")
    private static class MyEvent1 extends Event {
    }
    @Registered(false)
    @Category("Customized")
    @Name("MyEvent2")
    private static class MyEvent2 extends Event {
    }
    @Registered(false)
    @Category("Customized")
    @Name("MyEvent3")
    private static class MyEvent3 extends Event {
    }
}
