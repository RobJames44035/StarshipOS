/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test TestOnSpinWaitC1
 * @summary (x86 only) checks that java.lang.Thread.onSpinWait is intrinsified
 * @bug 8147844
 * @library /test/lib
 *
 * @requires vm.flagless
 * @requires os.arch=="x86" | os.arch=="amd64" | os.arch=="x86_64"
 * @requires vm.compiler1.enabled
 *
 * @run driver compiler.onSpinWait.TestOnSpinWaitC1
 */

package compiler.onSpinWait;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestOnSpinWaitC1 {

    public static void main(String[] args) throws Exception {

        // Test C1 compiler
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
          "-XX:+IgnoreUnrecognizedVMOptions", "-showversion",
          "-XX:+TieredCompilation", "-XX:TieredStopAtLevel=1", "-Xbatch",
          "-XX:+PrintCompilation", "-XX:+UnlockDiagnosticVMOptions",
          "-XX:+PrintInlining", Launcher.class.getName());

        OutputAnalyzer analyzer = new OutputAnalyzer(pb.start());

        analyzer.shouldHaveExitValue(0);
        analyzer.shouldContain("java.lang.Thread::onSpinWait (1 bytes)   intrinsic");
    }

    static class Launcher {

        public static void main(final String[] args) throws Exception {
            int end = 20_000;

            for (int i=0; i < end; i++) {
                test();
            }
        }
        static void test() {
            java.lang.Thread.onSpinWait();
        }
    }
}
