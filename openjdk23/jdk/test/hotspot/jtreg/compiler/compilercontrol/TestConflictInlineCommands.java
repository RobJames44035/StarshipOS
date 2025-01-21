/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @bug 8270459
 * @summary the last specified inlining option should overwrite all previous
 * @library /test/lib
 * @requires vm.flagless
 * @requires vm.compiler1.enabled | vm.compiler2.enabled
 *
 * @run driver compiler.compilercontrol.TestConflictInlineCommands
 */

package compiler.compilercontrol;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestConflictInlineCommands {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-Xbatch",
                "-XX:CompileCommand=inline,*TestConflictInlineCommands::caller",
                "-XX:CompileCommand=dontinline,*TestConflictInlineCommands::caller",
                "-XX:CompileCommand=quiet", "-XX:CompileCommand=compileonly,*Launcher::main",
                "-XX:+PrintCompilation", "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining",
                Launcher.class.getName());

        OutputAnalyzer analyzer = new OutputAnalyzer(pb.start());
        analyzer.shouldHaveExitValue(0);
        analyzer.shouldContain("disallowed by CompileCommand");
        analyzer.shouldNotContain("force inline by CompileCommand");

        pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-Xbatch",
                "-XX:CompileCommand=dontinline,*TestConflictInlineCommands::*caller",
                "-XX:CompileCommand=inline,*TestConflictInlineCommands::caller",
                "-XX:CompileCommand=quiet", "-XX:CompileCommand=compileonly,*Launcher::main",
                "-XX:+PrintCompilation", "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining",
                Launcher.class.getName());

        analyzer = new OutputAnalyzer(pb.start());
        analyzer.shouldHaveExitValue(0);
        analyzer.shouldContain("force inline by CompileCommand");
        analyzer.shouldNotContain("disallowed by CompileCommand");
    }

    static int sum;

    public static int caller(int a , int b) {
        return a + b;
    }

    static class Launcher {
        public static void main(String[] args) {
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 1000; j++) {
                    sum += caller(i, 0);
                }
            }
            System.out.println("sum is:" + sum);
            System.out.flush();
            System.err.flush();
        }
    }
}
