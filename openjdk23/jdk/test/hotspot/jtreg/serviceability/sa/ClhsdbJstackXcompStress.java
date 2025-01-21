/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.SA.SATestUtils;
import jdk.test.lib.Utils;
import jdk.test.lib.apps.LingeredApp;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @bug 8196969
 * @requires vm.hasSA
 * @requires vm.opt.DeoptimizeALot != true
 * @library /test/lib
 * @run driver/timeout=300 ClhsdbJstackXcompStress
 */
public class ClhsdbJstackXcompStress {

    private static final int MAX_ITERATIONS = 20;
    private static final boolean DEBUG = false;

    private static boolean isMatchCompiledFrame(List<String> output) {
        List<String> filtered = output.stream().filter( s -> s.contains("Compiled frame"))
                                               .collect(Collectors.toList());
        System.out.println("DEBUG: " + filtered);
        return !filtered.isEmpty() &&
               filtered.stream().anyMatch( s -> s.contains("LingeredAppWithRecComputation") );
    }

    private static void runJstackInLoop(LingeredApp app) throws Exception {
        boolean anyMatchedCompiledFrame = false;
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            JDKToolLauncher launcher = JDKToolLauncher
                    .createUsingTestJDK("jhsdb");
            launcher.addVMArgs(Utils.getFilteredTestJavaOpts("-showversion", "-Xcomp"));
            launcher.addToolArg("jstack");
            launcher.addToolArg("--pid");
            launcher.addToolArg(Long.toString(app.getPid()));

            ProcessBuilder pb = SATestUtils.createProcessBuilder(launcher);
            Process jhsdb = pb.start();
            OutputAnalyzer out = new OutputAnalyzer(jhsdb);

            jhsdb.waitFor();

            if (DEBUG) {
                System.out.println(out.getStdout());
                System.err.println(out.getStderr());
            }

            out.stderrShouldBeEmptyIgnoreDeprecatedWarnings();
            out.stdoutShouldNotContain("Error occurred during stack walking:");
            out.stdoutShouldContain(LingeredAppWithRecComputation.THREAD_NAME);
            List<String> stdoutList = Arrays.asList(out.getStdout().split("\\R"));
            anyMatchedCompiledFrame = anyMatchedCompiledFrame || isMatchCompiledFrame(stdoutList);
        }
        if (!anyMatchedCompiledFrame) {
             throw new RuntimeException("Expected jstack output to contain 'Compiled frame'");
        }
        System.out.println("DEBUG: jhsdb jstack did not throw NPE, as expected.");
    }

    public static void main(String... args) throws Exception {
        SATestUtils.skipIfCannotAttach(); // throws SkippedException if attach not expected to work.
        LingeredApp app = null;
        try {
            app = new LingeredAppWithRecComputation();
            LingeredApp.startApp(app,
                                 "-Xcomp",
                                 "-XX:CompileCommand=dontinline,LingeredAppWithRecComputation.factorial",
                                 "-XX:CompileCommand=compileonly,LingeredAppWithRecComputation.testLoop",
                                 "-XX:CompileCommand=compileonly,LingeredAppWithRecComputation.factorial");
            System.out.println("Started LingeredAppWithRecComputation with pid " + app.getPid());
            runJstackInLoop(app);
            System.out.println("Test Completed");
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        } finally {
            LingeredApp.stopApp(app);
        }
    }
}
