/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006298
 * @summary Using a bool (+/-) prefix on non-bool flag should result in a useful error message
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver NonBooleanFlagWithInvalidBooleanPrefix
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class NonBooleanFlagWithInvalidBooleanPrefix {
  public static void main(String[] args) throws Exception {
    ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
        "-XX:-MaxRAMPercentage=1", "-version");

    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    output.shouldContain("Unexpected +/- setting in VM option 'MaxRAMPercentage=1'");
    output.shouldHaveExitValue(1);

    pb = ProcessTools.createLimitedTestJavaProcessBuilder(
        "-XX:+MaxRAMPercentage=1", "-version");

    output = new OutputAnalyzer(pb.start());
    output.shouldContain("Unexpected +/- setting in VM option 'MaxRAMPercentage=1'");
    output.shouldHaveExitValue(1);

  }
}
