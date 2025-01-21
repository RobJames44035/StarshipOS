/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8042885
 * @summary Make sure there is no error using hexadecimal format in vm options
 * @author Yumin Qi
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestHexArguments
 */

import java.io.File;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestHexArguments {
    public static void main(String args[]) throws Exception {
      ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
          "-XX:SharedBaseAddress=0x1D000000", "-version");
      OutputAnalyzer output = new OutputAnalyzer(pb.start());
      output.shouldNotContain("Could not create the Java Virtual Machine");
      output.shouldHaveExitValue(0);

      pb = ProcessTools.createLimitedTestJavaProcessBuilder(
          "-XX:SharedBaseAddress=1D000000", "-version");
      output = new OutputAnalyzer(pb.start());
      output.shouldNotHaveExitValue(0);
      output.shouldContain("Could not create the Java Virtual Machine");
  }
}
