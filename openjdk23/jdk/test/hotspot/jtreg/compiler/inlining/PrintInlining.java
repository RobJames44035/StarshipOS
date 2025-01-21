/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8255742
 * @summary PrintInlining as compiler directive doesn't print virtual calls
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @requires vm.flagless
 *
 * @run driver compiler.inlining.PrintInlining
 */

package compiler.inlining;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class PrintInlining {

    static void test(String option) throws Exception {
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                "-XX:+IgnoreUnrecognizedVMOptions", "-showversion",
                "-server", "-XX:-TieredCompilation", "-Xbatch", "-XX:-UseOnStackReplacement",
                "-XX:CompileCommand=dontinline,*::bar",
                "-XX:CompileCommand=compileonly,*::foo",
                "-XX:+PrintCompilation", "-XX:+UnlockDiagnosticVMOptions", option,
                Launcher.class.getName());

        OutputAnalyzer analyzer = new OutputAnalyzer(pb.start());

        analyzer.shouldHaveExitValue(0);

        // The test is applicable only to C2 (present in Server VM).
        if (analyzer.getStderr().contains("Server VM")) {
            analyzer.outputTo(System.out);
            if (analyzer.asLines().stream()
                .filter(s -> s.matches(".*A::bar.+virtual call.*"))
                .count() != 1) {
                throw new Exception("'" + option + "' didn't print virtual call.");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        test("-XX:+PrintInlining");
        test("-XX:CompileCommand=option,*::foo,PrintInlining");
    }

    static class A {
        void bar() {}
    }

    static class B extends A {
        void bar() {}
    }

    static class C extends A {
        void bar() {}
    }

    static class D extends A {
        void bar() {}
    }

    static void foo(A a) {
        a.bar();
    }

    static class Launcher {
        public static void main(String[] args) throws Exception {
            A[] as = { new B(), new C(), new D() };
            for (int i = 0; i < 20_000; i++) {
                foo(as[i % 3]);
            }
        }
    }
}
