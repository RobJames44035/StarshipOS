/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @summary Verify that jcmd correctly reports that NMT is not enabled
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver JcmdWithNMTDisabled 1
 */

import jdk.test.lib.Platform;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.JDKToolFinder;

public class JcmdWithNMTDisabled {
  static ProcessBuilder pb = new ProcessBuilder();
  static String pid;

  public static void main(String args[]) throws Exception {

    // This test explicitly needs to be run with the exact command lines below, not passing on
    // arguments from the parent VM is a conscious choice to avoid NMT being turned on.
    if (args.length > 0) {
      ProcessBuilder pb;
      OutputAnalyzer output;
      String testjdkPath = System.getProperty("test.jdk");

      // First run without enabling NMT (not in debug, where NMT is by default on)
      if (!Platform.isDebugBuild()) {
        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Dtest.jdk=" + testjdkPath, "JcmdWithNMTDisabled");
        output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
      }

      // Then run with explicitly disabling NMT, should not be any difference
      pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Dtest.jdk=" + testjdkPath, "-XX:NativeMemoryTracking=off", "JcmdWithNMTDisabled");
      output = new OutputAnalyzer(pb.start());
      output.shouldHaveExitValue(0);

      return;
    }

    // Grab my own PID
    pid = Long.toString(ProcessTools.getProcessId());

    jcmdCommand("summary");
    jcmdCommand("detail");
    jcmdCommand("baseline");
    jcmdCommand("summary.diff");
    jcmdCommand("detail.diff");
    jcmdCommand("scale=GB");
  }

  // Helper method for invoking different jcmd calls, all should fail with the same message saying NMT is not enabled
  public static void jcmdCommand(String command) throws Exception {

    pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", command});
    OutputAnalyzer output = new OutputAnalyzer(pb.start());

    // Verify that jcmd reports that NMT is not enabled
    output.shouldContain("Native memory tracking is not enabled");
  }
}
