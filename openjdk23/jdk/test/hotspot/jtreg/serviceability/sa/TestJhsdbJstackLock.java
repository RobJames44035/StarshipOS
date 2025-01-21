/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.SA.SATestUtils;
import jdk.test.lib.Utils;

/**
 * @test
 * @bug 8185796 8335743
 * @requires vm.hasSA
 * @library /test/lib
 * @run driver TestJhsdbJstackLock
 */

public class TestJhsdbJstackLock {

    public static void main (String... args) throws Exception {
        SATestUtils.skipIfCannotAttach(); // throws SkippedException if attach not expected to work.
        LingeredApp app = null;

        try {
            app = new LingeredAppWithLock();
            LingeredApp.startApp(app);
            System.out.println ("Started LingeredApp with pid " + app.getPid());

            JDKToolLauncher launcher = JDKToolLauncher.createUsingTestJDK("jhsdb");
            launcher.addVMArgs(Utils.getFilteredTestJavaOpts("-showversion"));
            launcher.addToolArg("jstack");
            launcher.addToolArg("--pid");
            launcher.addToolArg(Long.toString(app.getPid()));

            ProcessBuilder pb = SATestUtils.createProcessBuilder(launcher);
            Process jhsdb = pb.start();
            OutputAnalyzer out = new OutputAnalyzer(jhsdb);

            jhsdb.waitFor();

            System.out.println(out.getStdout());
            System.err.println(out.getStderr());

            out.shouldMatch("^\\s+- locked <0x[0-9a-f]+> \\(a java\\.lang\\.Class for LingeredAppWithLock\\)$");
            out.shouldMatch("^\\s+- waiting to lock <0x[0-9a-f]+> \\(a java\\.lang\\.Class for LingeredAppWithLock\\)$");
            out.shouldMatch("^\\s+- locked <0x[0-9a-f]+> \\(a java\\.lang\\.Thread\\)$");
            out.shouldMatch("^\\s+- locked <0x[0-9a-f]+> \\(a java\\.lang\\.Class for int\\)$");
            out.shouldMatch("^\\s+- waiting on (<0x[0-9a-f]+> \\(a java\\.lang\\.Object\\)|<no object reference available>)$");

            out.stderrShouldBeEmptyIgnoreDeprecatedWarnings();

            System.out.println("Test Completed");
        } finally {
            LingeredApp.stopApp(app);
        }
    }
}
