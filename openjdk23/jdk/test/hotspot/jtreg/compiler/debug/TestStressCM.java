/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package compiler.debug;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Asserts;

/*
 * @test
 * @key stress randomness
 * @bug 8253765
 * @requires vm.debug == true & vm.compiler2.enabled
 * @summary Tests that, when compiling with StressLCM or StressGCM, using the
 *          same seed yields the same code motion trace.
 * @library /test/lib /
 * @run driver compiler.debug.TestStressCM StressLCM
 * @run driver compiler.debug.TestStressCM StressGCM
 */

public class TestStressCM {

    static String cmTrace(String stressOpt, int stressSeed) throws Exception {
        String className = TestStressCM.class.getName();
        String[] procArgs = {
            "-Xcomp", "-XX:-TieredCompilation", "-XX:-Inline",
            "-XX:CompileOnly=" + className + "::sum",
            "-XX:+TraceOptoPipelining", "-XX:+" + stressOpt,
            "-XX:StressSeed=" + stressSeed, className, "10"};
        ProcessBuilder pb  = ProcessTools.createLimitedTestJavaProcessBuilder(procArgs);
        OutputAnalyzer out = new OutputAnalyzer(pb.start());
        out.shouldHaveExitValue(0);
        // Extract the trace of our method (the last one after those of all
        // mandatory stubs such as _new_instance_Java, etc.).
        String [] traces = out.getStdout().split("\\R");
        int start = -1;
        for (int i = traces.length - 1; i >= 0; i--) {
            if (traces[i].contains("Start GlobalCodeMotion")) {
                start = i;
                break;
            }
        }
        // We should have found the start of the trace.
        Asserts.assertTrue(start >= 0,
            "could not find the code motion trace");
        String trace = "";
        for (int i = start; i < traces.length; i++) {
            trace += traces[i] + "\n";
        }
        return trace;
    }

    static void sum(int n) {
        int acc = 0;
        for (int i = 0; i < n; i++) acc += i;
        System.out.println(acc);
    }

    public static void main(String[] args) throws Exception {
        if (args[0].startsWith("Stress")) {
            String stressOpt = args[0];
            for (int s = 0; s < 10; s++) {
                Asserts.assertEQ(cmTrace(stressOpt, s), cmTrace(stressOpt, s),
                    "got different code motion traces for the same seed " + s);
            }
        } else if (args.length > 0) {
            sum(Integer.parseInt(args[0]));
        }
    }
}
