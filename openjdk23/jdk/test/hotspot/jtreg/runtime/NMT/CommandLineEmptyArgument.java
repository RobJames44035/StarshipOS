/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

 /*
 * @test
 * @summary Empty argument to NMT should result in an informative error message
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver CommandLineEmptyArgument
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class CommandLineEmptyArgument {

  public static void main(String args[]) throws Exception {
    ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:NativeMemoryTracking=");
    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    output.shouldContain("Syntax error, expecting -XX:NativeMemoryTracking=[off|summary|detail]");
    output.shouldHaveExitValue(1);
  }
}
