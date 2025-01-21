/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.debug;

import java.util.Random;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Utils;

/*
 * @test
 * @key stress randomness
 * @bug 8330157
 * @requires vm.debug == true & vm.compiler2.enabled & (vm.opt.AbortVMOnCompilationFailure == "null" | !vm.opt.AbortVMOnCompilationFailure)
 * @summary Basic tests for bailout stress flag.
 * @library /test/lib /
 * @run driver compiler.debug.TestStressBailout
 */

public class TestStressBailout {

    static void runTest(int invprob) throws Exception {
        String[] procArgs = {"-Xcomp", "-XX:-TieredCompilation", "-XX:+StressBailout",
                             "-XX:StressBailoutMean=" + invprob, "-version"};
        ProcessBuilder pb  = ProcessTools.createTestJavaProcessBuilder(procArgs);
        OutputAnalyzer out = new OutputAnalyzer(pb.start());
        out.shouldHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {
        Random r = Utils.getRandomInstance();
        // Likely bail out on -version, for some low Mean value.
        runTest(r.nextInt(1, 10));
        // Higher value
        runTest(r.nextInt(10, 1_000_000));
    }
}
