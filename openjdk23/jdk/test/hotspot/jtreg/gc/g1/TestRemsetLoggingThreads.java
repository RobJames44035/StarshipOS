/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestRemsetLoggingThreads
 * @requires vm.gc.G1
 * @bug 8025441 8145534
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management/sun.management
 * @summary Ensure that various values of worker threads/concurrent
 * refinement threads do not crash the VM.
 * @run driver gc.g1.TestRemsetLoggingThreads
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestRemsetLoggingThreads {

  private static void runTest(int refinementThreads, int workerThreads) throws Exception {
    OutputAnalyzer output = ProcessTools.executeLimitedTestJava("-XX:+UseG1GC",
                                                                "-XX:+UnlockDiagnosticVMOptions",
                                                                "-Xlog:gc+remset+exit=trace",
                                                                "-XX:G1ConcRefinementThreads=" + refinementThreads,
                                                                "-XX:ParallelGCThreads=" + workerThreads,
                                                                "-version");

    String pattern = "Concurrent refinement threads times \\(s\\)$";
    Matcher m = Pattern.compile(pattern, Pattern.MULTILINE).matcher(output.getStdout());

    if (!m.find()) {
      throw new Exception("Could not find correct output for concurrent RS threads times in stdout," +
        " should match the pattern \"" + pattern + "\", but stdout is \n" + output.getStdout());
    }
    output.shouldHaveExitValue(0);
  }

  public static void main(String[] args) throws Exception {
    // different valid combinations of number of refinement and gc worker threads
    runTest(1, 1);
    runTest(1, 5);
    runTest(5, 1);
    runTest(10, 10);
    runTest(1, 2);
    runTest(4, 3);
  }
}
