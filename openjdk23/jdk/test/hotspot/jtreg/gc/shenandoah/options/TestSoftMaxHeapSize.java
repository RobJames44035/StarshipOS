/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Test that Shenandoah checks SoftMaxHeapSize
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver TestSoftMaxHeapSize
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestSoftMaxHeapSize {
    public static void main(String[] args) throws Exception {
        {
            OutputAnalyzer output = ProcessTools.executeLimitedTestJava("-XX:+UnlockExperimentalVMOptions",
                    "-XX:+UseShenandoahGC",
                    "-Xms4m",
                    "-Xmx128m",
                    "-XX:SoftMaxHeapSize=4m",
                    "-version");
            output.shouldHaveExitValue(0);
        }

        {
            OutputAnalyzer output = ProcessTools.executeLimitedTestJava("-XX:+UnlockExperimentalVMOptions",
                    "-XX:+UseShenandoahGC",
                    "-Xms4m",
                    "-Xmx128m",
                    "-XX:SoftMaxHeapSize=128m",
                    "-version");
            output.shouldHaveExitValue(0);
        }

        {
            OutputAnalyzer output = ProcessTools.executeLimitedTestJava("-XX:+UnlockExperimentalVMOptions",
                    "-XX:+UseShenandoahGC",
                    "-Xms4m",
                    "-Xmx128m",
                    "-XX:SoftMaxHeapSize=129m",
                    "-version");
            output.shouldHaveExitValue(1);
            output.shouldContain("SoftMaxHeapSize must be less than or equal to the maximum heap size");
        }
    }
}
