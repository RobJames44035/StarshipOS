/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

 /*
 * @test
 * @summary Turning off NMT should not result in an error
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver CommandLineTurnOffNMT
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class CommandLineTurnOffNMT {

  public static void main(String args[]) throws Exception {
    ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
              "-XX:NativeMemoryTracking=off",
              "-version");
    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    output.shouldNotContain("error");
    output.shouldHaveExitValue(0);
  }
}
