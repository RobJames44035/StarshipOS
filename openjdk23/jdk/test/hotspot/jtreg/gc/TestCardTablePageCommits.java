/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package gc;

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test TestCardTablePageCommits
 * @bug 8059066
 * @summary Tests that the card table does not commit the same page twice
 * @requires vm.gc.Parallel
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @run driver gc.TestCardTablePageCommits
 */
public class TestCardTablePageCommits {
    public static void main(String args[]) throws Exception {
        // The test is run with a small heap to make sure all pages in the card
        // table gets committed. Need 8 MB heap to trigger the bug on SPARC
        // because of 8kB pages, assume 4 KB pages for all other CPUs.
        String Xmx = "-Xmx4m";

        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(
            Xmx,
            "-XX:NativeMemoryTracking=detail",
            "-XX:+UseParallelGC",
            "-version");
        output.shouldHaveExitValue(0);
    }
}
