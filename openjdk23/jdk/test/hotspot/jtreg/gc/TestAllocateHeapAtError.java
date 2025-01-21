/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc;

/* @test TestAllocateHeapAtError.java
 * @summary Test to check correct handling of non-existent directory passed to AllocateHeapAt option
 * @requires vm.gc != "Z" & os.family != "aix"
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @run driver gc.TestAllocateHeapAtError
 */

import java.io.File;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import java.util.UUID;

public class TestAllocateHeapAtError {
  public static void main(String args[]) throws Exception {
    String test_dir = System.getProperty("test.dir", ".");

    File f = null;
    do {
      f = new File(test_dir, UUID.randomUUID().toString());
    } while(f.exists());

    OutputAnalyzer output = ProcessTools.executeTestJava(
        "-XX:AllocateHeapAt=" + f.getName(),
        "-Xlog:gc+heap=info",
        "-Xmx32m",
        "-Xms32m",
        "-version");

    System.out.println("Output:\n" + output.getOutput());

    output.shouldContain("Could not create file for Heap");
    output.shouldContain("Error occurred during initialization of VM");
    output.shouldNotHaveExitValue(0);
  }
}
