/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jcmd;

import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.test.lib.jfr.FileHelper;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary Verify error when stopping with read-only file.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jcmd.TestJcmdStopReadOnlyFile
 */
public class TestJcmdStopReadOnlyFile {


    public static void main(String[] args) throws Exception {
        String name = "TestJcmdStopReadOnlyFile";
        Path readonlyFile = FileHelper.createReadOnlyFile(Paths.get(".", name + ".jfr"));
        if (!FileHelper.isReadOnlyPath(readonlyFile)) {
            System.out.println("Could not create read-only file. Ignoring test.");
            return;
        }

        OutputAnalyzer output = JcmdHelper.jcmd("JFR.start", "name=" + name);
        JcmdAsserts.assertRecordingHasStarted(output);
        JcmdHelper.waitUntilRunning(name);

        output = JcmdHelper.jcmd("JFR.stop",
                "name=" + name,
                "filename=" + readonlyFile.toAbsolutePath());
        JcmdAsserts.assertFileNotFoundException(output, name);
        JcmdHelper.assertRecordingIsRunning(name);
        JcmdHelper.stopAndCheck(name);
    }

}
