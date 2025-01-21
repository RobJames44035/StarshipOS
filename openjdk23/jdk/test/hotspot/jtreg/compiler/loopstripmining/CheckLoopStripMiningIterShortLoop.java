/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8196294
 * @summary when loop strip is enabled, LoopStripMiningIterShortLoop should be not null
 * @requires vm.flagless
 * @requires vm.flavor == "server"
 * @library /test/lib /
 * @run driver CheckLoopStripMiningIterShortLoop
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class CheckLoopStripMiningIterShortLoop {

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+UseG1GC", "-XX:+PrintFlagsFinal", "-version");
        OutputAnalyzer out = new OutputAnalyzer(pb.start());
        out.shouldHaveExitValue(0);

        long iter = Long.parseLong(out.firstMatch("uintx LoopStripMiningIter                      = (\\d+)", 1));
        long iterShort = Long.parseLong(out.firstMatch("uintx LoopStripMiningIterShortLoop             = (\\d+)", 1));

        if (iter <= 0 || iterShort <= 0) {
            throw new RuntimeException("Bad defaults for loop strip mining");
        }
    }
}
