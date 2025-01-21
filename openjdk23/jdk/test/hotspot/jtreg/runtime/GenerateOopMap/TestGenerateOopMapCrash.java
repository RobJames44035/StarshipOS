/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8291459
 * @summary Test that GenerateOopMap does not crash if last bytecode is a conditional branch
 * @library /test/lib /
 * @requires vm.flagless
 * @compile if_icmpleIsLastOpcode.jasm
 * @run driver TestGenerateOopMapCrash
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

// This test was copied from compiler test TestLinkageErrorInGenerateOopMap.java.
public class TestGenerateOopMapCrash {

    public static void main(String args[]) throws Exception {
        if (args.length == 0) {
            // Spawn new VM instance to execute test
            ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                    "-XX:-TieredCompilation",
                    "-XX:CompileCommand=dontinline,if_icmpleIsLastOpcode.m*",
                    "-Xmx64m",
                    TestGenerateOopMapCrash.class.getName(),
                    "run");
            OutputAnalyzer output = new OutputAnalyzer(pb.start());
            output.shouldHaveExitValue(0);
        } else {
            // Execute test
            if_icmpleIsLastOpcode.test();
        }
    }
}
