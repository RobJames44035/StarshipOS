/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8199137
 * @summary VerifyStringTableAtExit should not crash
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @run driver StringTableVerifyTest
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class StringTableVerifyTest {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder("-XX:+UnlockDiagnosticVMOptions", "-XX:+VerifyStringTableAtExit", "-version");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);
    }
}

