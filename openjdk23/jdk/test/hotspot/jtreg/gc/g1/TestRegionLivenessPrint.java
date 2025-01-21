/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestRegionLivenessPrint.java
 * @bug 8151920
 * @requires vm.gc.G1
 * @summary Make sure that G1 does not assert when printing region liveness data on a humongous continues region.
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UseG1GC -Xmx128M -XX:G1HeapRegionSize=1m -Xlog:gc+liveness=trace gc.g1.TestRegionLivenessPrint
 */

import jdk.test.whitebox.WhiteBox;

public class TestRegionLivenessPrint {

  static byte[] bigobj = new byte[1024* 1024 * 2];

  public static void main(String[] args) throws InterruptedException {
      WhiteBox wb = WhiteBox.getWhiteBox();
      // Run a concurrent mark cycle to trigger the liveness accounting log messages.
      wb.g1RunConcurrentGC();
  }

}
