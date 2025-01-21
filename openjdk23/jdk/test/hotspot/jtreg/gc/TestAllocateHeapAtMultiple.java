/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc;

/* @test TestAllocateHeapAtMultiple.java
 * @summary Test to check allocation of Java Heap with AllocateHeapAt option. Has multiple sub-tests to cover different code paths.
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @requires vm.bits == "64" & vm.gc != "Z" & os.family != "aix"
 * @run driver/timeout=360 gc.TestAllocateHeapAtMultiple
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import java.util.ArrayList;
import java.util.Collections;

public class TestAllocateHeapAtMultiple {
  public static void main(String args[]) throws Exception {
    ArrayList<String> flags = new ArrayList<>();

    String test_dir = System.getProperty("test.dir", ".");

    // Extra flags for each of the sub-tests
    String[][] extraFlagsList = new String[][] {
      {"-Xmx32m", "-Xms32m", "-XX:+UseCompressedOops"},     // 1. With compressedoops enabled.
      {"-Xmx32m", "-Xms32m", "-XX:-UseCompressedOops"},     // 2. With compressedoops disabled.
      {"-Xmx32m", "-Xms32m", "-XX:HeapBaseMinAddress=3g"},  // 3. With user specified HeapBaseMinAddress.
      {"-Xmx32m", "-Xms32m", "-XX:+UseLargePages"},         // 4. Set UseLargePages.
      {"-Xmx32m", "-Xms32m", "-XX:+UseNUMA"}                // 5. Set UseNUMA.
    };

    for (String[] extraFlags : extraFlagsList) {
      flags.clear();
      // Add extra flags specific to the sub-test.
      Collections.addAll(flags, extraFlags);
      // Add common flags
      Collections.addAll(flags, new String[] {"-XX:AllocateHeapAt=" + test_dir,
                                              "-Xlog:gc+heap=info",
                                              "-version"});

      OutputAnalyzer output = ProcessTools.executeTestJava(flags);

      System.out.println("Output:\n" + output.getOutput());

      output.shouldContain("Successfully allocated Java heap at location");
      output.shouldHaveExitValue(0);
    }
  }
}
