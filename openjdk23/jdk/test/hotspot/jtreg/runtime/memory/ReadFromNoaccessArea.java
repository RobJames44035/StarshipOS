/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @summary Test that touching noaccess area in class ReservedHeapSpace results in SIGSEGV/ACCESS_VIOLATION
 * @library /test/lib
 * @requires vm.bits == 64
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver ReadFromNoaccessArea
 */

import jdk.test.lib.Platform;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.whitebox.WhiteBox;
import jtreg.SkippedException;

public class ReadFromNoaccessArea {

  public static void main(String args[]) throws Exception {

    ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
          "-Xbootclasspath/a:.",
          "-XX:+UnlockDiagnosticVMOptions",
          "-XX:+WhiteBoxAPI",
          "-XX:+UseCompressedOops",
          "-XX:HeapBaseMinAddress=33G",
          "-XX:-CreateCoredumpOnCrash",
          "-Xmx128m",
          DummyClassWithMainTryingToReadFromNoaccessArea.class.getName());

    OutputAnalyzer output = new OutputAnalyzer(pb.start());
    System.out.println("******* Printing stdout for analysis in case of failure *******");
    System.out.println(output.getStdout());
    System.out.println("******* Printing stderr for analysis in case of failure *******");
    System.out.println(output.getStderr());
    System.out.println("***************************************************************");
    if (output.getStdout() != null && output.getStdout().contains("WB_ReadFromNoaccessArea method is useless")) {
      throw new SkippedException("There is no protected page in ReservedHeapSpace in these circumstance");
    }
    output.shouldNotHaveExitValue(0);
    if (Platform.isWindows()) {
      output.shouldContain("EXCEPTION_ACCESS_VIOLATION");
    } else if (Platform.isOSX()) {
      output.shouldContain("SIGBUS");
    } else {
      output.shouldContain("SIGSEGV");
    }
  }

  public static class DummyClassWithMainTryingToReadFromNoaccessArea {

    // This method calls whitebox method reading from noaccess area
    public static void main(String args[]) throws Exception {
      WhiteBox.getWhiteBox().readFromNoaccessArea();
    }
  }

}
