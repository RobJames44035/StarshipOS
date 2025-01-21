/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test id=enabled
 * @bug 8281822
 * @summary Test DTrace options are accepted on suitable builds
 * @requires vm.flagless
 * @requires vm.hasDTrace
 *
 * @library /test/lib
 * @run driver DTraceOptionsTest true
 */

/*
 * @test id=disabled
 * @bug 8281822
 * @summary Test DTrace options are rejected on unsuitable builds
 * @requires vm.flagless
 * @requires !vm.hasDTrace
 *
 * @library /test/lib
 * @run driver DTraceOptionsTest disabled
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class DTraceOptionsTest {
    public static void main(String[] args) throws Throwable {
        boolean dtraceEnabled;
        if (args.length > 0) {
            dtraceEnabled = Boolean.parseBoolean(args[0]);
        } else {
            throw new IllegalArgumentException("Should provide the argument");
        }

        String[] options = {
            "DTraceMethodProbes",
            "DTraceAllocProbes",
            "DTraceMonitorProbes",
        };

        for (String opt : options) {
            var pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+" + opt, "-version");
            var oa = new OutputAnalyzer(pb.start());
            if (dtraceEnabled) {
                oa.shouldHaveExitValue(0);
            } else {
                oa.shouldNotHaveExitValue(0);
                oa.shouldContain(opt + " flag is not applicable for this configuration");
            }
        }
    }

}
