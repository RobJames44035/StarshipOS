/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Test that Shenandoah GC thread counts are handled well
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestThreadCounts
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestThreadCounts {
    public static void main(String[] args) throws Exception {
        for (int conc = 0; conc < 16; conc++) {
            for (int par = 0; par < 16; par++) {
                testWith(conc, par);
            }
        }
    }

    private static void testWith(int conc, int par) throws Exception {
        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(
                "-Xmx128m",
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+UnlockExperimentalVMOptions",
                "-XX:+UseShenandoahGC",
                "-XX:ConcGCThreads=" + conc,
                "-XX:ParallelGCThreads=" + par,
                "-version");

        if (conc == 0) {
            output.shouldContain("Shenandoah expects ConcGCThreads > 0");
            output.shouldHaveExitValue(1);
        } else if (par == 0) {
            output.shouldContain("Shenandoah expects ParallelGCThreads > 0");
            output.shouldHaveExitValue(1);
        } else if (conc > par) {
            output.shouldContain("Shenandoah expects ConcGCThreads <= ParallelGCThreads");
            output.shouldHaveExitValue(1);
        } else {
            output.shouldNotContain("Shenandoah expects ConcGCThreads <= ParallelGCThreads");
            output.shouldHaveExitValue(0);
        }
    }

}
