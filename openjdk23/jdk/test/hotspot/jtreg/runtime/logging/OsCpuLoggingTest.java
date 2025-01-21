/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8151939
 * @summary os+cpu output should contain some os,cpu information
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver OsCpuLoggingTest
 */

import java.io.File;
import java.util.Map;
import jdk.test.lib.Platform;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class OsCpuLoggingTest {

    static void analyzeOutputForOsLog(OutputAnalyzer output) throws Exception {
        output.shouldContain("SafePoint Polling address");
        output.shouldHaveExitValue(0);
    }

    static void analyzeOutputForOsCpuLog(OutputAnalyzer output) throws Exception {
        output.shouldContain("CPU: total");
        output.shouldHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:os+cpu", "-version");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        analyzeOutputForOsCpuLog(output);

        // PPC64 only uses polling pages when UseSIGTRAP is off.
        pb = (Platform.isPPC() && Platform.is64bit())
             ? ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:os", "-XX:-UseSIGTRAP", "-version")
             : ProcessTools.createLimitedTestJavaProcessBuilder("-Xlog:os", "-version");
        output = new OutputAnalyzer(pb.start());
        analyzeOutputForOsLog(output);
    }
}
