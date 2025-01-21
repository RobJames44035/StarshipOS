/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestUnrecognizedVMOptionsHandling
 * @bug 8017611
 * @summary Tests handling unrecognized VM options
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.arguments.TestUnrecognizedVMOptionsHandling
 */

import jdk.test.lib.process.OutputAnalyzer;

public class TestUnrecognizedVMOptionsHandling {

  public static void main(String args[]) throws Exception {
    // The first two JAVA processes are expected to fail, but with a correct VM option suggestion
    OutputAnalyzer outputWithError = GCArguments.executeLimitedTestJava(
      "-XX:+UseDynamicNumberOfGcThreads",
      "-version"
      );
    outputWithError.shouldContain("Did you mean '(+/-)UseDynamicNumberOfGCThreads'?");
    if (outputWithError.getExitValue() == 0) {
      throw new RuntimeException("Not expected to get exit value 0");
    }

    outputWithError = GCArguments.executeLimitedTestJava(
      "-XX:MaxiumHeapSize=500m",
      "-version"
      );
    outputWithError.shouldContain("Did you mean 'MaxHeapSize=<value>'?");
    if (outputWithError.getExitValue() == 0) {
      throw new RuntimeException("Not expected to get exit value 0");
    }

    // The last JAVA process should run successfully for the purpose of sanity check
    OutputAnalyzer outputWithNoError = GCArguments.executeLimitedTestJava(
      "-XX:+UseDynamicNumberOfGCThreads",
      "-version"
      );
    outputWithNoError.shouldNotContain("Did you mean '(+/-)UseDynamicNumberOfGCThreads'?");
    outputWithNoError.shouldHaveExitValue(0);
  }
}
