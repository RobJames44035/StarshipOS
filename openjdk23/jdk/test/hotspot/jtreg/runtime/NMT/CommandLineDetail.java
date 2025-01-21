/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

 /*
 * @test
 * @summary Running with NMT detail should not result in an error
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver CommandLineDetail
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class CommandLineDetail {

  public static void main(String args[]) throws Exception {

    ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
      "-XX:NativeMemoryTracking=detail",
      "-version");
    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    output.shouldNotContain("error");
    output.shouldHaveExitValue(0);
  }
}
