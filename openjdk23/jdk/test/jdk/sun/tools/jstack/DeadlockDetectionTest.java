/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.apps.LingeredAppWithDeadlock;
import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Utils;

import jtreg.SkippedException;

/**
 * @test
 * @summary Test deadlock detection
 * @requires vm.hasSA
 * @library /test/lib
 * @build jdk.test.lib.apps.*
 * @build DeadlockDetectionTest
 * @run main DeadlockDetectionTest
 */
public class DeadlockDetectionTest {

    private static LingeredAppWithDeadlock theApp = null;
    private static ProcessBuilder processBuilder = new ProcessBuilder();

    private static OutputAnalyzer jstack(String... toolArgs) throws Exception {
        JDKToolLauncher launcher = JDKToolLauncher.createUsingTestJDK("jstack");
        launcher.addVMArgs(Utils.getFilteredTestJavaOpts("-XX:+UsePerfData"));
        launcher.addVMArg("-XX:+UsePerfData");
        if (toolArgs != null) {
            for (String toolArg : toolArgs) {
                launcher.addToolArg(toolArg);
            }
        }

        processBuilder.command(launcher.getCommand());
        System.out.println(processBuilder.command().stream().collect(Collectors.joining(" ")));
        OutputAnalyzer output = ProcessTools.executeProcess(processBuilder);
        System.out.println(output.getOutput());

        return output;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting DeadlockDetectionTest");

        if (!LingeredApp.isLastModifiedWorking()) {
            // Exact behaviour of the test depends on operating system and the test nature,
            // so just print the warning and continue
            System.err.println("Warning! Last modified time doesn't work.");
        }

        try {
            String[] vmArgs = Utils.appendTestJavaOpts("-XX:+UsePerfData");

            theApp = new LingeredAppWithDeadlock();
            LingeredApp.startApp(theApp, vmArgs);
            OutputAnalyzer output = jstack(Long.toString(theApp.getPid()));
            System.out.println(output.getOutput());

            if (output.getExitValue() == 3) {
                throw new SkippedException("Test can't run for some reason");
            } else {
                output.shouldHaveExitValue(0);
                output.shouldContain("Found 1 deadlock.");
            }
        } finally {
            LingeredApp.stopApp(theApp);
        }
    }
}
