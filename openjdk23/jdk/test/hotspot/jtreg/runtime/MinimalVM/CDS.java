/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @requires vm.flavor == "minimal"
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @run driver CDS
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class CDS {

    public static void main(String args[]) throws Exception {
        ProcessBuilder pb;

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-minimal", "-Xshare:dump");
        new OutputAnalyzer(pb.start())
                .shouldContain("Shared spaces are not supported in this VM")
                .shouldHaveExitValue(1);

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-minimal", "-Xshare:on");
        new OutputAnalyzer(pb.start())
                .shouldContain("Shared spaces are not supported in this VM")
                .shouldHaveExitValue(1);

        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-minimal", "-Xshare:auto", "-version");
        new OutputAnalyzer(pb.start())
                .shouldContain("Shared spaces are not supported in this VM")
                .shouldHaveExitValue(0);
    }
}
