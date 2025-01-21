/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @run driver compiler.runtime.unloaded.TestUnloadedSignatureClass
 */

package compiler.runtime.unloaded;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestUnloadedSignatureClass {
    static class Test {
        static int test(Integer i) {
            // Bound to a wrapper around a method with (Ljava/lang/Object;ILjava/util/function/BiPredicate;Ljava/util/List;)I signature.
            // Neither BiPredicate nor List are guaranteed to be resolved by the context class loader.
            return switch (i) {
                case null -> -1;
                case 0    ->  0;
                default   ->  1;
            };
        }

        public static void main(String[] args) {
            for (int i = 0; i < 20_000; i++) {
                test(i);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
                "-Xbatch", "-XX:-TieredCompilation",
                "-XX:CompileCommand=quiet", "-XX:CompileCommand=compileonly,*::test",
                "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintCompilation", "-XX:+PrintInlining",
                Test.class.getName()
        );

        OutputAnalyzer out = new OutputAnalyzer(pb.start());
        out.shouldHaveExitValue(0);
        out.shouldNotContain("unloaded signature classes");
    }
}
