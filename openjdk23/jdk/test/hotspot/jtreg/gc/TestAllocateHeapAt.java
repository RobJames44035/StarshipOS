/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc;

/* @test TestAllocateHeapAt.java
 * @summary Test to check allocation of Java Heap with AllocateHeapAt option
 * @requires vm.gc != "Z" & os.family != "aix"
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @run driver gc.TestAllocateHeapAt
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestAllocateHeapAt {
  public static void main(String args[]) throws Exception {
    OutputAnalyzer output = ProcessTools.executeTestJava(
        "-XX:AllocateHeapAt=" + System.getProperty("test.dir", "."),
        "-Xlog:gc+heap=info",
        "-Xmx32m",
        "-Xms32m",
        "-version");

    System.out.println("Output:\n" + output.getOutput());

    output.shouldContain("Successfully allocated Java heap at location");
    output.shouldHaveExitValue(0);
  }
}
