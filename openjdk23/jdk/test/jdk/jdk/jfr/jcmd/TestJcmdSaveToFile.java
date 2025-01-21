/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.jfr.jcmd;

import java.io.File;

import jdk.test.lib.jfr.FileHelper;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary The test verifies that recording can be written to a file both with JFR.start and JFR.stop
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jcmd.TestJcmdSaveToFile
 */
public class TestJcmdSaveToFile {

    public static void main(String[] args) throws Exception {
        testStartAndSave();
        testStopAndSave();
    }

    private static void testStartAndSave() throws Exception {
        String name = "testStartAndSave";
        File recording = new File(name + ".jfr");
        OutputAnalyzer output = JcmdHelper.jcmd("JFR.start",
                "name=" + name,
                "duration=1h",
                "filename=" + recording.getAbsolutePath());
        JcmdAsserts.assertRecordingHasStarted(output);
        JcmdHelper.waitUntilRunning(name);
        JcmdHelper.stopAndCheck(name);
        FileHelper.verifyRecording(recording);
    }

    private static void testStopAndSave() throws Exception {
        String name = "testStopAndSave";
        File recording = new File(name + ".jfr");
        OutputAnalyzer output = JcmdHelper.jcmd("JFR.start", "name=" + name);
        JcmdAsserts.assertRecordingHasStarted(output);
        JcmdHelper.waitUntilRunning(name);
        JcmdHelper.stopWriteToFileAndCheck(name, recording);
        FileHelper.verifyRecording(recording);
    }
}
