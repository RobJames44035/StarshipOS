/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.PrintStream;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.SA.SATestUtils;

import jtreg.SkippedException;

/**
 * @test
 * @bug 8265505
 * @summary Test clhsdb command which should be run on debugd server
 * @requires vm.hasSA
 * @requires os.family != "windows"
 * @library /test/lib
 * @run driver RunCommandOnServerTest
 */

public class RunCommandOnServerTest {

    public static void main(String[] args) throws Exception {
        SATestUtils.skipIfCannotAttach(); // throws SkippedException if attach not expected to work.
        SATestUtils.validateSADebugDPrivileges();

        LingeredApp theApp = null;
        DebugdUtils debugd = null;
        try {
            theApp = LingeredApp.startApp();
            System.out.println("Started LingeredApp with pid " + theApp.getPid());
            debugd = new DebugdUtils();
            debugd.attach(theApp.getPid());

            JDKToolLauncher jhsdbLauncher = JDKToolLauncher.createUsingTestJDK("jhsdb");
            jhsdbLauncher.addToolArg("clhsdb");
            jhsdbLauncher.addToolArg("--connect");
            jhsdbLauncher.addToolArg("localhost");

            Process jhsdb = (SATestUtils.createProcessBuilder(jhsdbLauncher)).start();
            OutputAnalyzer out = new OutputAnalyzer(jhsdb);

            try (PrintStream console = new PrintStream(jhsdb.getOutputStream(), true)) {
                console.println("echo true");
                console.println("verbose true");
                console.println("findsym gHotSpotVMTypes");
                console.println("quit");
            }

            jhsdb.waitFor();
            System.out.println(out.getStdout());
            System.err.println(out.getStderr());

            out.stderrShouldBeEmptyIgnoreDeprecatedWarnings();
            out.shouldMatch("^0x[0-9a-f]+: .+/libjvm\\.(so|dylib) \\+ 0x[0-9a-f]+$");
            out.shouldHaveExitValue(0);

            // This will detect most SA failures, including during the attach.
            out.shouldNotMatch("^sun.jvm.hotspot.debugger.DebuggerException:.*$");
            // This will detect unexpected exceptions, like NPEs and asserts, that are caught
            // by sun.jvm.hotspot.CommandProcessor.
            out.shouldNotMatch("^Error: .*$");
        } catch (SkippedException se) {
            throw se;
        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        } finally {
            if (debugd != null) {
                debugd.detach();
            }
            LingeredApp.stopApp(theApp);
        }
        System.out.println("Test PASSED");
    }
}
