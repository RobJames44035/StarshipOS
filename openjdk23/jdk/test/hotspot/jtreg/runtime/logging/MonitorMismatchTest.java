/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */


/*
 * @test MonitorMismatchTest
 * @bug 8150084
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @compile MonitorMismatchHelper.jasm
 * @run driver MonitorMismatchTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Platform;

public class MonitorMismatchTest {

    public static void main(String... args) throws Exception {
        // monitormismatch should turn on.
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xcomp",
                                                                             "-XX:+TieredCompilation",
                                                                             "-Xlog:monitormismatch=info",
                                                                             "MonitorMismatchHelper");
        OutputAnalyzer o = new OutputAnalyzer(pb.start());
        o.shouldHaveExitValue(0);
        o.shouldContain("[monitormismatch] Monitor mismatch in method");

        // monitormismatch should turn off.
        pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xcomp",
                                                              "-XX:+TieredCompilation",
                                                              "-Xlog:monitormismatch=off",
                                                              "MonitorMismatchHelper");
        o = new OutputAnalyzer(pb.start());
        o.shouldHaveExitValue(0);
        o.shouldNotContain("[monitormismatch]");
    };

}
