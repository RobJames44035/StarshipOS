/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestInitialTenuringThreshold
 * @bug 8014765
 * @requires vm.gc.Parallel & vm.opt.InitialTenuringThreshold == null & vm.opt.MaxTenuringThreshold == null
 * @summary Tests argument processing for initial tenuring threshold
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.arguments.TestInitialTenuringThreshold
 * @author thomas.schatzl@oracle.com
 */

import jdk.test.lib.process.OutputAnalyzer;

public class TestInitialTenuringThreshold {

  public static void runWithThresholds(int initial, int max, boolean shouldfail) throws Exception {
    OutputAnalyzer output = GCArguments.executeTestJava(
      "-XX:+UseParallelGC",
      "-XX:InitialTenuringThreshold=" + String.valueOf(initial),
      "-XX:MaxTenuringThreshold=" + String.valueOf(max),
      "-version"
      );

    if (shouldfail) {
      output.shouldHaveExitValue(1);
    } else {
      output.shouldHaveExitValue(0);
    }
  }


  public static void main(String args[]) throws Exception {
    OutputAnalyzer output = GCArguments.executeTestJava(
      // some value below the default value of InitialTenuringThreshold of 7
      "-XX:+UseParallelGC",
      "-XX:MaxTenuringThreshold=1",
      "-version"
      );

    output.shouldHaveExitValue(0);
    // successful tests
    runWithThresholds(0, 10, false);
    runWithThresholds(5, 5, false);
    runWithThresholds(8, 16, false);
    // failing tests
    runWithThresholds(10, 0, true);
    runWithThresholds(9, 8, true);
    runWithThresholds(-1, 8, true);
    runWithThresholds(0, -1, true);
    runWithThresholds(8, -1, true);
    runWithThresholds(16, 8, true);
    runWithThresholds(8, 17, true);
  }
}
