/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8276422
 * @summary Invalid/missing values for the finalization option should be rejected
 * @library /test/lib
 * @run driver InvalidFinalizationOption
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class InvalidFinalizationOption {
    public static void main(String[] args) throws Exception {
        record TestData(String arg, String expected) { }

        TestData[] testData = {
            new TestData("--finalization",        "Unrecognized option"),
            new TestData("--finalization=",       "Invalid finalization value"),
            new TestData("--finalization=azerty", "Invalid finalization value")
        };

        for (var data : testData) {
            ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(data.arg);
            OutputAnalyzer output = new OutputAnalyzer(pb.start());
            output.shouldContain(data.expected);
            output.shouldHaveExitValue(1);
        }
    }
}
