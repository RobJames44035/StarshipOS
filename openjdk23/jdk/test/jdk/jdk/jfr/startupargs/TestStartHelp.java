/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
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
 * @run main jdk.jfr.startupargs.TestStartHelp
 */
public class TestStartHelp {

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("-XX:StartFlightRecording:help");
        OutputAnalyzer out = ProcessTools.executeProcess(pb);
        out.shouldContain("Syntax : -XX:StartFlightRecording:[options]");
        out.shouldContain("options are separated with a comma.");
        out.shouldHaveExitValue(0);
    }
}
