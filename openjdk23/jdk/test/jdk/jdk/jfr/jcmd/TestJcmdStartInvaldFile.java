/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.jfr.jcmd;

import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary Verify error when starting with invalid file.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jcmd.TestJcmdStartInvaldFile
 */
public class TestJcmdStartInvaldFile {

    private final static String ILLEGAL_FILE_NAME = ":;/\\?";

    public static void main(String[] args) throws Exception {
        String name = "testStartWithIllegalFilename";
        OutputAnalyzer output = JcmdHelper.jcmd("JFR.start",
                "name=" + name,
                "duration=10s",
                "filename=" + ILLEGAL_FILE_NAME);
        JcmdAsserts.assertNotAbleToWriteToFile(output);
        JcmdHelper.assertRecordingNotExist(name);
    }

}
