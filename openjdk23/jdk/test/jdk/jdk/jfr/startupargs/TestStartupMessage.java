/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.jfr.startupargs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main jdk.jfr.startupargs.TestStartupMessage
 */
public class TestStartupMessage {

    public static class TestMessage {
        public static void main(String[] args) throws Exception {
        }
    }

    public static void main(String[] args) throws Exception {
         startJfrJvm("-Xlog:jfr+startup=off")
             .shouldNotContain("[jfr,startup")
             .shouldNotContain("Started recording")
             .shouldNotContain("Use jcmd");

         startJfrJvm("-Xlog:jfr+startup=error")
             .shouldNotContain("[jfr,startup")
             .shouldNotContain("Started recording")
             .shouldNotContain("Use jcmd");

         // Known limitation.
         // Can't turn off log with -Xlog:jfr+startup=warning

         startJfrJvm()
             .shouldContain("[info][jfr,startup")
             .shouldContain("Started recording")
             .shouldContain("Use jcmd");

         startJfrJvm("-Xlog:jfr+startup=info")
             .shouldContain("[info][jfr,startup")
             .shouldContain("Started recording")
             .shouldContain("Use jcmd");
    }

    private static OutputAnalyzer startJfrJvm(String... args) throws Exception {
        List<String> commands = new ArrayList<>(Arrays.asList(args));
        commands.add("-XX:StartFlightRecording");
        commands.add(TestMessage.class.getName());
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(commands);
        OutputAnalyzer out = ProcessTools.executeProcess(pb);
        out.shouldHaveExitValue(0);
        return out;
    }
}
