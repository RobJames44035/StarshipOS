/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package gc.logging;

/*
 * @test TestGCId
 * @bug 8043607
 * @summary Ensure that the GCId is logged
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI gc.logging.TestGCId
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jtreg.SkippedException;
import jdk.test.whitebox.gc.GC;

public class TestGCId {
  public static void main(String[] args) throws Exception {
    boolean noneGCSupported = true;

    if (GC.Parallel.isSupported()) {
      noneGCSupported = false;
      testGCId("UseParallelGC");
    }
    if (GC.G1.isSupported()) {
      noneGCSupported = false;
      testGCId("UseG1GC");
    }
    if (GC.Serial.isSupported()) {
      noneGCSupported = false;
      testGCId("UseSerialGC");
    }
    if (GC.Shenandoah.isSupported()) {
      noneGCSupported = false;
      testGCId("UseShenandoahGC");
    }

    if (noneGCSupported) {
      throw new SkippedException("Skipping test because none of Parallel/G1/Serial/Shenandoah is supported.");
    }
  }

  private static void verifyContainsGCIDs(OutputAnalyzer output) {
    output.shouldMatch("\\[.*\\]\\[.*\\]\\[.*\\] GC\\(0\\) ");
    output.shouldMatch("\\[.*\\]\\[.*\\]\\[.*\\] GC\\(1\\) ");
    output.shouldHaveExitValue(0);
  }

  private static void testGCId(String gcFlag) throws Exception {
    OutputAnalyzer output =
      ProcessTools.executeLimitedTestJava("-XX:+UnlockExperimentalVMOptions", "-XX:+" + gcFlag, "-Xlog:gc", "-Xmx10M", GCTest.class.getName());
    verifyContainsGCIDs(output);
  }

  static class GCTest {
    private static byte[] garbage;
    public static void main(String [] args) {
      System.out.println("Creating garbage");
      // create 128MB of garbage. This should result in at least one GC
      for (int i = 0; i < 1024; i++) {
        garbage = new byte[128 * 1024];
      }
      // do a system gc to get one more gc
      System.gc();
      System.out.println("Done");
    }
  }
}
