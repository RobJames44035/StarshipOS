/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestG1HeapRegionSize
 * @bug 8021879
 * @requires vm.gc.G1 & vm.opt.G1HeapRegionSize == null
 * @summary Verify that the flag G1HeapRegionSize is updated properly
 * @modules java.base/jdk.internal.misc
 * @modules java.management/sun.management
 * @library /test/lib
 * @library /
 * @run driver gc.arguments.TestG1HeapRegionSize
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.Arrays;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.Platform;

public class TestG1HeapRegionSize {

  private static void checkG1HeapRegionSize(String[] flags, int expectedValue, int exitValue) throws Exception {
    ArrayList<String> flagList = new ArrayList<String>();
    flagList.addAll(Arrays.asList(flags));
    flagList.add("-XX:+UseG1GC");
    flagList.add("-XX:+PrintFlagsFinal");
    flagList.add("-version");

    OutputAnalyzer output = GCArguments.executeTestJava(flagList);
    output.shouldHaveExitValue(exitValue);

    if (exitValue == 0) {
      String stdout = output.getStdout();
      int flagValue = getFlagValue("G1HeapRegionSize", stdout);
      if (flagValue != expectedValue) {
        throw new RuntimeException("Wrong value for G1HeapRegionSize. Expected " + expectedValue + " but got " + flagValue);
      }
    }
  }

  private static int getFlagValue(String flag, String where) {
    Matcher m = Pattern.compile(flag + "\\s+:?=\\s+\\d+").matcher(where);
    if (!m.find()) {
      throw new RuntimeException("Could not find value for flag " + flag + " in output string");
    }
    String match = m.group();
    return Integer.parseInt(match.substring(match.lastIndexOf(" ") + 1, match.length()));
  }

  public static void main(String args[]) throws Exception {
    final int M = 1024 * 1024;

    checkG1HeapRegionSize(new String[] { "-Xmx64m"   /* default is 1m */        },  1*M, 0);
    checkG1HeapRegionSize(new String[] { "-Xmx64m",  "-XX:G1HeapRegionSize=2m"  },  2*M, 0);
    checkG1HeapRegionSize(new String[] { "-Xmx64m",  "-XX:G1HeapRegionSize=3m"  },  4*M, 0);
    checkG1HeapRegionSize(new String[] { "-Xmx256m", "-XX:G1HeapRegionSize=32m" }, 32*M, 0);
    if (Platform.is64bit()) {
      checkG1HeapRegionSize(new String[] { "-Xmx4096m", "-XX:G1HeapRegionSize=64m" }, 64*M, 0);
      checkG1HeapRegionSize(new String[] { "-Xmx4096m", "-XX:G1HeapRegionSize=512m" }, 512*M, 0);
      checkG1HeapRegionSize(new String[] { "-Xmx4096m", "-XX:G1HeapRegionSize=1024m" }, 512*M, 1);
      checkG1HeapRegionSize(new String[] { "-Xmx128g" }, 32*M, 0);
    } else {
      checkG1HeapRegionSize(new String[] { "-Xmx256m", "-XX:G1HeapRegionSize=64m" }, 64*M, 1);
    }
  }
}
