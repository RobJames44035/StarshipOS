/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.startupargs;

import jdk.jfr.Recording;
import jdk.test.lib.Asserts;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main jdk.jfr.startupargs.TestStartName
 */
public class TestStartName {

    public static class TestName {
        public static void main(String[] args) throws Exception {
            Recording r = StartupHelper.getRecording(args[0]);
            Asserts.assertNotNull(r);
        }
    }

    private static void testName(String recordingName, boolean validName) throws Exception {
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
            "-XX:StartFlightRecording:name=" + recordingName, TestName.class.getName(), recordingName);
        OutputAnalyzer out = ProcessTools.executeProcess(pb);

        if (validName) {
            out.shouldHaveExitValue(0);
        } else {
            out.shouldHaveExitValue(1);
            out.shouldContain("Name of recording can't be numeric");
        }
    }

    public static void main(String[] args) throws Exception {
        testName("12345a", true);
        testName("--12345", true);
        testName("[()]", true);

        // numeric names should not be accepted
        testName("100", false);
        testName("-327", false);
        testName("+511", false);
    }
}
