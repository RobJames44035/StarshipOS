/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8262271
 * @requires vm.hasSA
 * @library /test/lib
 * @run main/timeout=240 JStackStressTest
 */

import java.io.IOException;
import java.io.OutputStream;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.SA.SATestUtils;
import jdk.test.lib.Utils;

public class JStackStressTest {

    static Process jShellProcess;

    public static void testjstack() throws IOException {
        launchJshell();
        long jShellPID = jShellProcess.pid();
        OutputAnalyzer jshellOutput = new OutputAnalyzer(jShellProcess);

        try {
            // Do 4 jstacks on the jshell process as it starts up
            for (int i = 1; i <= 4; i++) {
                JDKToolLauncher launcher = JDKToolLauncher.createUsingTestJDK("jhsdb");
                launcher.addVMArgs(Utils.getTestJavaOpts());
                launcher.addToolArg("jstack");
                launcher.addToolArg("--pid=" + Long.toString(jShellPID));

                System.out.println("###### Starting jstack iteration " + i + " against " + jShellPID);
                long startTime = System.currentTimeMillis();
                ProcessBuilder processBuilder = SATestUtils.createProcessBuilder(launcher);
                OutputAnalyzer output = ProcessTools.executeProcess(processBuilder);
                System.out.println("jhsdb jstack stdout:");
                System.out.println(output.getStdout());
                System.out.println("jhsdb jstack stderr:");
                System.out.println(output.getStderr());
                long elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("###### End of all output for iteration " + i +
                                   " which took " + elapsedTime + "ms");
                output.shouldHaveExitValue(0);
                // This will detect most SA failures, including during the attach.
                output.shouldNotMatch("^sun.jvm.hotspot.debugger.DebuggerException:.*$");
                // This will detect unexpected exceptions, like NPEs and asserts, that are caught
                // by sun.jvm.hotspot.tools.Tool.execute().
                output.shouldNotMatch("^Error: .*$");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        } finally {
            try (OutputStream out = jShellProcess.getOutputStream()) {
                out.write("/exit\n".getBytes());
                out.flush();
            }
            try {
                jShellProcess.waitFor(); // jshell should exit quickly
            } catch (InterruptedException e) {
            }
            System.out.println("jshell Output: " + jshellOutput.getOutput());
        }
    }

    public static void launchJshell() throws IOException {
        System.out.println("Starting Jshell");
        long startTime = System.currentTimeMillis();
        try {
            JDKToolLauncher launcher = JDKToolLauncher.createUsingTestJDK("jshell");
            launcher.addVMArgs(Utils.getTestJavaOpts());
            ProcessBuilder pb = new ProcessBuilder(launcher.getCommand());
            jShellProcess = ProcessTools.startProcess("JShell", pb);
        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        }
    }

    public static void main(String[] args) throws Exception {
        SATestUtils.skipIfCannotAttach(); // throws SkippedException if attach not expected to work.
        testjstack();

        // The test throws RuntimeException on error.
        // IOException is thrown if Jshell can't start because of some bad
        // environment condition
        System.out.println("Test PASSED");
    }
}
