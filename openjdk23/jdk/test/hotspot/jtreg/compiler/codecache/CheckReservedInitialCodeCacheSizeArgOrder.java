/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8013496
 * @summary Test checks that the order in which ReversedCodeCacheSize and
 *          InitialCodeCacheSize are passed to the VM is irrelevant.
 * @library /test/lib
 * @requires vm.flagless
 *
 * @run driver compiler.codecache.CheckReservedInitialCodeCacheSizeArgOrder
 */

package compiler.codecache;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class CheckReservedInitialCodeCacheSizeArgOrder {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb1,  pb2;
        OutputAnalyzer out1, out2;

        pb1 = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:InitialCodeCacheSize=4m", "-XX:ReservedCodeCacheSize=8m", "-version");
        pb2 = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:ReservedCodeCacheSize=8m", "-XX:InitialCodeCacheSize=4m", "-version");

        out1 = new OutputAnalyzer(pb1.start());
        out2 = new OutputAnalyzer(pb2.start());

        // Check that the outputs are equal
        if (out1.getStdout().compareTo(out2.getStdout()) != 0) {
            throw new RuntimeException("Test failed");
        }

        out1.shouldHaveExitValue(0);
        out2.shouldHaveExitValue(0);
    }
}
