/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8023014
 * @summary Test ensures that there is no crash if there is not enough ReservedCodeCacheSize
 *          to initialize all compiler threads. The option -Xcomp gives the VM more time to
 *          trigger the old bug.
 * @library /test/lib
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run driver compiler.startup.SmallCodeCacheStartup
 */

package compiler.startup;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import static jdk.test.lib.Asserts.assertTrue;

public class SmallCodeCacheStartup {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:ReservedCodeCacheSize=3m",
                                                                             "-XX:CICompilerCount=64",
                                                                             "-Xcomp",
                                                                             "-version");
        OutputAnalyzer analyzer = new OutputAnalyzer(pb.start());
        try {
            analyzer.shouldHaveExitValue(0);
        } catch (RuntimeException e) {
            // Error occurred during initialization, did we run out of adapter space?
            assertTrue(analyzer.getOutput().contains("VirtualMachineError: Out of space in CodeCache"),
                    "Expected VirtualMachineError");
        }

        System.out.println("TEST PASSED");
  }
}
