/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8321479
 * @summary VM should not crash with property "-D-D"
 * @requires vm.flagless
 * @library /test/lib
 * @run driver UnrecognizedProperty
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class UnrecognizedProperty {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-D-D");

        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("Usage: java");
        output.shouldHaveExitValue(1);
    }
}
