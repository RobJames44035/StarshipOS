/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.tool;

import java.nio.file.Path;

import jdk.jfr.EventType;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary Test jfr info
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.tool.TestSummary
 */
public class TestSummary {

    public static void main(String[] args) throws Throwable {
        Path f = ExecuteHelper.createProfilingRecording().toAbsolutePath();
        String file = f.toAbsolutePath().toString();

        OutputAnalyzer output = ExecuteHelper.jfr("summary");
        output.shouldContain("missing file");

        output = ExecuteHelper.jfr("summary", "--wrongOption", file);
        output.shouldContain("too many arguments");

        output = ExecuteHelper.jfr("summary", file);
        try (RecordingFile rf = new RecordingFile(f)) {
            for (EventType t : rf.readEventTypes()) {
                output.shouldContain(t.getName());
            }
        }
        output.shouldContain("Version");
    }
}
