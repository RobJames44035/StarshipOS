/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test TestBasicLogOutput
 * @bug 8203370
 * @summary Ensure -XX:+JVMCIPrintProperties successfully prints expected output to stdout.
 * @requires vm.flagless
 * @requires vm.jvmci
 * @library /test/lib
 * @run driver TestJVMCIPrintProperties
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestJVMCIPrintProperties {

    public static void main(String[] args) throws Exception {
        test("-XX:+EnableJVMCI");
        test("-XX:+UseJVMCICompiler");
    }

    static void test(String enableFlag) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
            "-XX:+UnlockExperimentalVMOptions",
            enableFlag, "-Djvmci.Compiler=null",
            "-XX:+JVMCIPrintProperties");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("[JVMCI properties]"); // expected message
        output.shouldContain("jvmci.Compiler := \"null\""); // expected message
        output.shouldContain("jvmci.PrintConfig = false"); // expected message
        output.shouldHaveExitValue(0);
    }
}
