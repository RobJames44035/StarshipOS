/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8030783
 * @summary Regression test for 8026478
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run driver compiler.debug.VerifyAdapterSharing
 */

package compiler.debug;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class VerifyAdapterSharing {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb;
        OutputAnalyzer out;

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xcomp", "-XX:+IgnoreUnrecognizedVMOptions",
                "-XX:+VerifyAdapterSharing", "-version");
        out = new OutputAnalyzer(pb.start());
        out.shouldHaveExitValue(0);
    }
}
