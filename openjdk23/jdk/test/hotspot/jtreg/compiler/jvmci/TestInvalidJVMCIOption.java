/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test TestInvalidJVMCIOption
 * @bug 8257220
 * @summary Ensures invalid JVMCI options do not crash the VM with a hs-err log.
 * @requires vm.flagless
 * @requires vm.jvmci
 * @library /test/lib
 * @run driver TestInvalidJVMCIOption
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestInvalidJVMCIOption {

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+EagerJVMCI",
            "-XX:+UseJVMCICompiler",
            "-Djvmci.XXXXXXXXX=true");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        String expectStdout = String.format(
            "Error parsing JVMCI options: Could not find option jvmci.XXXXXXXXX%n" +
            "Error: A fatal exception has occurred. Program will exit.%n");

        // Test for containment instead of equality as -XX:+EagerJVMCI means
        // the main thread and one or more libjvmci compiler threads
        // may initialize libjvmci at the same time and thus the error
        // message can appear multiple times.
        output.stdoutShouldContain(expectStdout);

        output.stderrShouldBeEmpty();
        output.shouldHaveExitValue(1);
    }
}
