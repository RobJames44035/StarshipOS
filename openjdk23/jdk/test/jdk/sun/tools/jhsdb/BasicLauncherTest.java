/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @summary Basic test for jhsdb launcher
 * @library /test/lib
 * @requires vm.hasSA
 * @build jdk.test.lib.apps.*
 * @run main BasicLauncherTest
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.Platform;
import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.SA.SATestUtils;
import jdk.test.lib.Utils;

public class BasicLauncherTest {

    private static LingeredApp theApp = null;
    private static boolean useJavaLauncher = false;

    private static JDKToolLauncher createSALauncher() {
        JDKToolLauncher launcher = null;
        if (useJavaLauncher) {
            // Use java launcher if we need to pass additional parameters to VM
            // for debugging purpose
            // e.g. -Xlog:class+load=info:file=/tmp/BasicLauncherTest.log
            launcher = JDKToolLauncher.createUsingTestJDK("java");
            launcher.addToolArg("sun.jvm.hotspot.SALauncher");
        }
        else {
            launcher = JDKToolLauncher.createUsingTestJDK("jhsdb");
        }
        launcher.addVMArgs(Utils.getFilteredTestJavaOpts("-Xcomp"));
        return launcher;
    }

    public static void launchCLHSDB()
        throws IOException {

        System.out.println("Starting LingeredApp");
        try {
            theApp = LingeredApp.startApp();

            System.out.println("Starting clhsdb against " + theApp.getPid());
            JDKToolLauncher launcher = createSALauncher();
            launcher.addToolArg("clhsdb");
            launcher.addToolArg("--pid=" + Long.toString(theApp.getPid()));

            ProcessBuilder processBuilder = SATestUtils.createProcessBuilder(launcher);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process toolProcess = processBuilder.start();

            try (OutputStream out = toolProcess.getOutputStream()) {
                out.write("universe\n".getBytes());
                out.write("quit\n".getBytes());
            }

            // By default child process output stream redirected to pipe, so we are reading it in foreground.
            Exception unexpected = null;
            try (BufferedReader reader =
                 new BufferedReader(new InputStreamReader(toolProcess.getInputStream()))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    System.out.println(line);

                    if (line.contains("unknown subtype of CollectedHeap")) {
                        unexpected = new RuntimeException("CollectedHeap type should be known.");
                        break;
                    }
                }
            }

            toolProcess.waitFor();

            if (toolProcess.exitValue() != 0) {
                throw new RuntimeException("FAILED CLHSDB terminated with non-zero exit code " + toolProcess.exitValue());
            }

            if (unexpected != null) {
                throw unexpected;
            }

        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        } finally {
            LingeredApp.stopApp(theApp);
        }
    }

    public static void launchJStack() throws IOException {
        System.out.println("Starting LingeredApp");
        try {
            theApp = LingeredApp.startApp("-Xmx256m");

            System.out.println("Starting jstack against " + theApp.getPid());
            JDKToolLauncher launcher = createSALauncher();

            launcher.addToolArg("jstack");
            launcher.addToolArg("--pid=" + Long.toString(theApp.getPid()));

            ProcessBuilder processBuilder = SATestUtils.createProcessBuilder(launcher);
            OutputAnalyzer output = ProcessTools.executeProcess(processBuilder);
            output.shouldContain("No deadlocks found");
            output.shouldNotContain("illegal bci");
            output.shouldNotContain("AssertionFailure");
            output.shouldHaveExitValue(0);

        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        } finally {
            LingeredApp.stopApp(theApp);
        }
    }

    /**
     *
     * @param vmArgs  - vm and java arguments to launch test app
     * @return exit code of tool
     */
    public static void launch(String expectedMessage,
                 Optional<String> unexpectedMessage, List<String> toolArgs)
        throws IOException {

        System.out.println("Starting LingeredApp");
        try {
            theApp = LingeredApp.startApp("-Xmx256m");

            System.out.println("Starting " + toolArgs.get(0) + " against " + theApp.getPid());
            JDKToolLauncher launcher = createSALauncher();

            for (String cmd : toolArgs) {
                launcher.addToolArg(cmd);
            }

            launcher.addToolArg("--pid=" + Long.toString(theApp.getPid()));

            ProcessBuilder processBuilder = SATestUtils.createProcessBuilder(launcher);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            OutputAnalyzer output = ProcessTools.executeProcess(processBuilder);
            output.shouldContain(expectedMessage);
            unexpectedMessage.ifPresent(output::shouldNotContain);
            output.shouldHaveExitValue(0);

        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        } finally {
            LingeredApp.stopApp(theApp);
        }
    }

    public static void launch(String expectedMessage,
                              String unexpectedMessage, String... toolArgs)
        throws IOException {

        launch(expectedMessage, Optional.ofNullable(unexpectedMessage),
                                                       Arrays.asList(toolArgs));
    }

    public static void main(String[] args) throws Exception {
        SATestUtils.skipIfCannotAttach(); // throws SkippedException if attach not expected to work.

        launchCLHSDB();

        launch("compiler detected", null, "jmap", "--clstats");
        launchJStack();
        launch("compiler detected", null, "jmap");
        launch("Java System Properties",
               "System Properties info not available", "jinfo");
        launch("java.threads", null, "jsnap");

        // The test throws RuntimeException on error.
        // IOException is thrown if LingeredApp can't start because of some bad
        // environment condition
        System.out.println("Test PASSED");
    }
}
