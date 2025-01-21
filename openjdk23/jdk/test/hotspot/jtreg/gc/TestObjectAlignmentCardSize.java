/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package gc;

/* @test TestObjectAlignmentCardSize.java
 * @summary Test to check correct handling of ObjectAlignmentInBytes and GCCardSizeInBytes combinations
 * @requires vm.gc != "Z"
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @run driver gc.TestObjectAlignmentCardSize
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestObjectAlignmentCardSize {
  private static void runTest(int objectAlignment, int cardSize, boolean shouldSucceed) throws Exception {
    OutputAnalyzer output = ProcessTools.executeTestJava(
        "-XX:ObjectAlignmentInBytes=" + objectAlignment,
        "-XX:GCCardSizeInBytes=" + cardSize,
        "-Xmx32m",
        "-Xms32m",
        "-version");

    System.out.println("Output:\n" + output.getOutput());

    if (shouldSucceed) {
      output.shouldHaveExitValue(0);
    } else {
      output.shouldContain("Invalid combination of GCCardSizeInBytes and ObjectAlignmentInBytes");
      output.shouldNotHaveExitValue(0);
    }
  }

  public static void main(String[] args) throws Exception {
    runTest(8, 512, true);
    runTest(128, 128, true);
    runTest(256, 128, false);
    runTest(256, 256, true);
    runTest(256, 512, true);
  }
}
