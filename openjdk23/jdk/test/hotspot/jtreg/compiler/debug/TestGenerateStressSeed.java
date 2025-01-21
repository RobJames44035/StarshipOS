/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package compiler.debug;

import java.nio.file.Paths;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/*
 * @test
 * @key stress randomness
 * @bug 8252219 8256535
 * @requires vm.compiler2.enabled
 * @summary Tests that using a stress option without -XX:StressSeed=N generates
 *          and logs a random seed.
 * @library /test/lib /
 * @run driver compiler.debug.TestGenerateStressSeed StressLCM
 * @run driver compiler.debug.TestGenerateStressSeed StressGCM
 * @run driver compiler.debug.TestGenerateStressSeed StressIGVN
 * @run driver compiler.debug.TestGenerateStressSeed StressCCP
 * @run driver compiler.debug.TestGenerateStressSeed StressMacroExpansion
 */

public class TestGenerateStressSeed {

    static void sum(int n) {
        int acc = 0;
        for (int i = 0; i < n; i++) acc += i;
        System.out.println(acc);
    }

    public static void main(String[] args) throws Exception {
        if (args[0].startsWith("Stress")) {
            String className = TestGenerateStressSeed.class.getName();
            String stressOpt = args[0];
            String log = "test.log";
            String[] procArgs = {
                "-Xcomp", "-XX:-TieredCompilation", "-XX:+UnlockDiagnosticVMOptions",
                "-XX:CompileOnly=" + className + "::sum", "-XX:+" + stressOpt,
                "-XX:+LogCompilation", "-XX:LogFile=" + log, className, "10"};
            new OutputAnalyzer(ProcessTools.createLimitedTestJavaProcessBuilder(procArgs).start())
                .shouldHaveExitValue(0);
            new OutputAnalyzer(Paths.get(log))
                .shouldContain("stress_test seed");
        } else {
            sum(Integer.parseInt(args[0]));
        }
    }
}
