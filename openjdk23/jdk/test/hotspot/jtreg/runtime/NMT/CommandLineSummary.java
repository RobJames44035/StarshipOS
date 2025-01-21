/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

 /*
 * @test
 * @summary Running with NMT summary should not result in an error
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver CommandLineSummary
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class CommandLineSummary {

  public static void main(String args[]) throws Exception {

    ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
      "-XX:NativeMemoryTracking=summary",
      "-version");
    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    output.shouldNotContain("error");
    output.shouldHaveExitValue(0);
  }
}
