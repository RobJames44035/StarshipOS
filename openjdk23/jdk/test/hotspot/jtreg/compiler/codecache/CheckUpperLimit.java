/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8015635
 * @summary Test ensures that the ReservedCodeCacheSize is at most MAXINT
 * @library /test/lib
 * @requires vm.flagless
 *
 * @run driver compiler.codecache.CheckUpperLimit
 */

package compiler.codecache;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class CheckUpperLimit {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb;
        OutputAnalyzer out;

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:ReservedCodeCacheSize=2049m", "-version");
        out = new OutputAnalyzer(pb.start());
        out.shouldContain("Invalid ReservedCodeCacheSize=");
        out.shouldHaveExitValue(1);
    }
}
