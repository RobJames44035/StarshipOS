/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8030955
 * @summary Allow multiple calls to PerfMemory::destroy() without asserting.
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver PerfMemDestroy
 */

import java.io.File;
import java.util.Map;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class PerfMemDestroy {
    public static void main(String args[]) throws Throwable {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+PerfAllowAtExitRegistration", "-version");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
    }
}
