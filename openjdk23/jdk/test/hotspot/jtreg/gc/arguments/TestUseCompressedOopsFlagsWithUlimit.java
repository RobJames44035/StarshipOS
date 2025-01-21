/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestUseCompressedOopsFlagsWithUlimit
 * @bug 8280761
 * @summary Verify correct UseCompressedOops when MaxRAM and MaxRAMPercentage
 * are specified with ulimit -v.
 * @library /test/lib
 * @library /
 * @requires vm.bits == "64"
 * @requires os.family == "linux"
 * @requires vm.gc != "Z"
 * @requires vm.opt.UseCompressedOops == null
 * @run driver gc.arguments.TestUseCompressedOopsFlagsWithUlimit
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.Arrays;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestUseCompressedOopsFlagsWithUlimit {

  private static void checkFlag(long ulimit, long maxram, int maxrampercent, boolean expectcoop) throws Exception {

    ArrayList<String> args = new ArrayList<String>();
    args.add("-XX:MaxRAM=" + maxram);
    args.add("-XX:MaxRAMPercentage=" + maxrampercent);
    args.add("-XX:+PrintFlagsFinal");
    args.add("-version");

    // Convert bytes to kbytes for ulimit -v
    var ulimit_prefix = "ulimit -v " + (ulimit / 1024);

    String cmd = ProcessTools.getCommandLine(ProcessTools.createTestJavaProcessBuilder(args));
    OutputAnalyzer output = ProcessTools.executeProcess("sh", "-c", ulimit_prefix + ";" + cmd);
    output.shouldHaveExitValue(0);
    String stdout = output.getStdout();

    boolean actualcoop = getFlagBoolValue("UseCompressedOops", stdout);
    if (actualcoop != expectcoop) {
      throw new RuntimeException("UseCompressedOops set to " + actualcoop +
        ", expected " + expectcoop + " when running with the following flags: " + Arrays.asList(args).toString());
    }
  }

  private static boolean getFlagBoolValue(String flag, String where) {
    Matcher m = Pattern.compile(flag + "\\s+:?= (true|false)").matcher(where);
    if (!m.find()) {
      throw new RuntimeException("Could not find value for flag " + flag + " in output string");
    }
    return m.group(1).equals("true");
  }

  public static void main(String args[]) throws Exception {
    // Tests
    // Verify that UseCompressedOops Ergo follows ulimit -v setting.

    long oneG = 1L * 1024L * 1024L * 1024L;

    // Args: ulimit, max_ram, max_ram_percent, expected_coop
    // Setting MaxRAMPercentage explicitly to make the test more resilient.
    checkFlag(10 * oneG, 32 * oneG, 100, true);
    checkFlag(10 * oneG, 128 * oneG, 100, true);
  }
}
