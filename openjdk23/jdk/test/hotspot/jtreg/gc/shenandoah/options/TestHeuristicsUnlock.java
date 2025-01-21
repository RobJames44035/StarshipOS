/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @summary Test that Shenandoah heuristics are unlocked properly
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestHeuristicsUnlock
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestHeuristicsUnlock {

    enum Mode {
        PRODUCT,
        DIAGNOSTIC,
        EXPERIMENTAL,
    }

    public static void main(String[] args) throws Exception {
        testWith("-XX:ShenandoahGCHeuristics=adaptive",   Mode.PRODUCT);
        testWith("-XX:ShenandoahGCHeuristics=static",     Mode.PRODUCT);
        testWith("-XX:ShenandoahGCHeuristics=compact",    Mode.PRODUCT);
        testWith("-XX:ShenandoahGCHeuristics=aggressive", Mode.DIAGNOSTIC);
    }

    private static void testWith(String h, Mode mode) throws Exception {
        {
            OutputAnalyzer output = ProcessTools.executeLimitedTestJava(
                    "-Xmx128m",
                    "-XX:-UnlockDiagnosticVMOptions",
                    "-XX:-UnlockExperimentalVMOptions",
                    "-XX:+UseShenandoahGC",
                    h,
                    "-version"
            );
            switch (mode) {
                case PRODUCT:
                    output.shouldHaveExitValue(0);
                    break;
                case DIAGNOSTIC:
                case EXPERIMENTAL:
                    output.shouldNotHaveExitValue(0);
                    break;
            }
        }

        {
            OutputAnalyzer output = ProcessTools.executeLimitedTestJava(
                    "-Xmx128m",
                    "-XX:+UnlockDiagnosticVMOptions",
                    "-XX:-UnlockExperimentalVMOptions",
                    "-XX:+UseShenandoahGC",
                    h,
                    "-version"
            );
            switch (mode) {
                case PRODUCT:
                case DIAGNOSTIC:
                    output.shouldHaveExitValue(0);
                    break;
                case EXPERIMENTAL:
                    output.shouldNotHaveExitValue(0);
                    break;
            }
        }

        {
            OutputAnalyzer output = ProcessTools.executeLimitedTestJava(
                    "-Xmx128m",
                    "-XX:-UnlockDiagnosticVMOptions",
                    "-XX:+UnlockExperimentalVMOptions",
                    "-XX:+UseShenandoahGC",
                    h,
                    "-version"
            );
            switch (mode) {
                case PRODUCT:
                case EXPERIMENTAL:
                    output.shouldHaveExitValue(0);
                    break;
                case DIAGNOSTIC:
                    output.shouldNotHaveExitValue(0);
                    break;
            }
        }
    }

}
