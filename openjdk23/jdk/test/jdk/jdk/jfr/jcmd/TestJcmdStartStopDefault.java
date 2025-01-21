/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.jfr.jcmd;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.FileHelper;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary Start a recording without name.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jcmd.TestJcmdStartStopDefault
 */
public class TestJcmdStartStopDefault {

    public static void main(String[] args) throws Exception {
        Path recording = Paths.get(".","TestJcmdStartStopDefault.jfr").toAbsolutePath().normalize();

        OutputAnalyzer output = JcmdHelper.jcmd("JFR.start");
        JcmdAsserts.assertRecordingHasStarted(output);

        String name = parseRecordingName(output);
        JcmdHelper.waitUntilRunning(name);

        output = JcmdHelper.jcmd("JFR.dump",
                "name=" + name,
                "filename=" + recording);
        JcmdAsserts.assertRecordingDumpedToFile(output, recording.toFile());
        JcmdHelper.stopAndCheck(name);
        FileHelper.verifyRecording(recording.toFile());
    }

    private static String parseRecordingName(OutputAnalyzer output) {
        // Expected output:
        // Started recording recording-1. No limit (duration/maxsize/maxage) in use.
        // Use JFR.dump name=recording-1 filename=FILEPATH to copy recording data to file.

        String stdout = output.getStdout();
        Pattern p = Pattern.compile(".*Use jcmd \\d+ JFR.dump name=(\\S+).*", Pattern.DOTALL);
        Matcher m = p.matcher(stdout);
        Asserts.assertTrue(m.matches(), "Could not parse recording name");
        String name = m.group(1);
        System.out.println("Recording name=" + name);
        return name;
    }
}
