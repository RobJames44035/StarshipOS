/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8265465 8267075
 * @summary Test jcmd to dump static shared archive.
 * @requires vm.cds
 * @library /test/lib /test/hotspot/jtreg/runtime/cds/appcds /test/hotspot/jtreg/runtime/cds/appcds/test-classes
 * @modules jdk.jcmd/sun.tools.common:+open
 * @build jdk.test.lib.apps.LingeredApp jdk.test.whitebox.WhiteBox Hello
 *        JCmdTestDumpBase JCmdTestLingeredApp JCmdTestFileSafety
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm/timeout=480 -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI JCmdTestFileSafety
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.nio.file.Files;
import java.nio.file.Paths;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.Platform;
import jdk.test.lib.process.OutputAnalyzer;

public class JCmdTestFileSafety extends JCmdTestDumpBase {
    static final String promptStdout = "please check stdout file";
    static final String promptStderr = "or stderr file";

    static void checkContainAbsoluteLogPath(OutputAnalyzer output) throws Exception {
       String stdText = output.getOutput();
       if (stdText.contains(promptStdout) &&
           stdText.contains(promptStderr)) {
           int a = stdText.indexOf(promptStdout);
           int b = stdText.indexOf(promptStderr);
           String stdOutFileName = stdText.substring(a + promptStdout.length() + 1, b - 1).trim();
           File   stdOutFile = new File(stdOutFileName);
           if (!stdOutFile.isAbsolute()) {
               throw new RuntimeException("Failed to set file name in absolute for prompting message");
           }
        }
    }

    private static void removeFile(String fileName) throws Exception {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    static void test() throws Exception {
        buildJars();

        LingeredApp app = null;
        long pid;

        int  test_count = 1;
        String subDir    = "subdir";
        File outputDirFile = new File(subDir);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdir();
        }
        outputDirFile.setWritable(true);
        String localFileName = subDir + File.separator + "MyStaticDump.jsa";
        removeFile(localFileName);

        setIsStatic(true/*static*/);
        // Set target dir not writable, do static dump
        print2ln(test_count++ + " Set target dir not writable, do static dump");
        setKeepArchive(true);
        app = createLingeredApp("-cp", allJars);
        pid = app.getPid();
        test(localFileName, pid, noBoot,  EXPECT_PASS);
        checkFileExistence(localFileName, true/*exist*/);
        FileTime ft1 = Files.getLastModifiedTime(Paths.get(localFileName));
        outputDirFile.setWritable(false);
        test(localFileName, pid, noBoot,  EXPECT_FAIL);
        FileTime ft2 = Files.getLastModifiedTime(Paths.get(localFileName));
        if (!ft2.equals(ft1)) {
            throw new RuntimeException("Archive file " + localFileName + " should not be updated");
        }
        removeFile(localFileName);
        outputDirFile.setWritable(true);

        // Illegal character in file name
        localFileName = "mystatic:.jsa";
        OutputAnalyzer output = test(localFileName, pid, noBoot,  EXPECT_FAIL);
        checkFileExistence(localFileName, false/*exist*/);
        checkContainAbsoluteLogPath(output);
        app.stopApp();

        setIsStatic(false/*dynamic*/);
        //  Set target dir not writable, do dynamic dump
        print2ln(test_count++ + " Set target dir not writable, do dynamic dump");
        setKeepArchive(true);
        outputDirFile.setWritable(true);
        app = createLingeredApp("-cp", allJars, "-XX:+RecordDynamicDumpInfo");
        pid = app.getPid();
        localFileName = subDir + File.separator + "MyDynamicDump.jsa";
        test(localFileName, pid, noBoot,  EXPECT_PASS);
        checkFileExistence(localFileName, true/*exist*/);
        ft1 = Files.getLastModifiedTime(Paths.get(localFileName));
        outputDirFile.setWritable(false);
        test(localFileName, pid, noBoot,  EXPECT_FAIL);
        ft2 = Files.getLastModifiedTime(Paths.get(localFileName));
        if (!ft2.equals(ft1)) {
            throw new RuntimeException("Archive file " + localFileName + " should not be updated");
        }
        app.stopApp();
        removeFile(localFileName);
        outputDirFile.setWritable(true);
        outputDirFile.delete();
    }

    public static void main(String... args) throws Exception {
        if (Platform.isWindows()) {
            // ON windows, File operation resulted difference from other OS.
            // Set dir to not accessible for write, we still can run the test
            // to create archive successfully which is not expected.
            throw new jtreg.SkippedException("Test skipped on Windows");
        }
        runTest(JCmdTestFileSafety::test);
    }
}
