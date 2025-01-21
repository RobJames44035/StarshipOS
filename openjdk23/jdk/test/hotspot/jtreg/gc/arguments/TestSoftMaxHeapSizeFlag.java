/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestSoftMaxHeapSizeFlag
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.arguments.TestSoftMaxHeapSizeFlag
 */

import jdk.test.lib.process.ProcessTools;

public class TestSoftMaxHeapSizeFlag {
    // Note: Xms and Xmx values get aligned up by HeapAlignment which is 32M with 64k pages.
    private static final long Xms              = 224 * 1024 * 1024;
    private static final long Xmx              = 320 * 1024 * 1024;
    private static final long greaterThanXmx   = Xmx + 1;
    private static final long betweenXmsAndXmx = (Xms + Xmx) / 2;

    public static void main(String args[]) throws Exception {
        // Test default value
        ProcessTools.executeTestJava("-Xms" + Xms, "-Xmx" + Xmx,
                                     "-XX:+PrintFlagsFinal", "-version")
                    .shouldMatch("SoftMaxHeapSize[ ]+=[ ]+" + Xmx)
                    .shouldHaveExitValue(0);

        // Test setting small value
        ProcessTools.executeTestJava("-Xms" + Xms, "-Xmx" + Xmx,
                                     "-XX:SoftMaxHeapSize=" + Xms,
                                     "-XX:+PrintFlagsFinal", "-version")
                    .shouldMatch("SoftMaxHeapSize[ ]+=[ ]+" + Xms)
                    .shouldHaveExitValue(0);

        // Test setting middle value
        ProcessTools.executeTestJava("-Xms" + Xms, "-Xmx" + Xmx,
                                     "-XX:SoftMaxHeapSize=" + betweenXmsAndXmx,
                                     "-XX:+PrintFlagsFinal", "-version")
                    .shouldMatch("SoftMaxHeapSize[ ]+=[ ]+" + betweenXmsAndXmx)
                    .shouldHaveExitValue(0);

        // Test setting largest value
        ProcessTools.executeTestJava("-Xms" + Xms, "-Xmx" + Xmx,
                                     "-XX:SoftMaxHeapSize=" + Xmx,
                                     "-XX:+PrintFlagsFinal", "-version")
                    .shouldMatch("SoftMaxHeapSize[ ]+=[ ]+" + Xmx)
                    .shouldHaveExitValue(0);

        // Test setting a too large value
        ProcessTools.executeTestJava("-Xms" + Xms, "-Xmx" + Xmx,
                                     "-XX:SoftMaxHeapSize=" + greaterThanXmx,
                                     "-XX:+PrintFlagsFinal", "-version")
                    .shouldContain("SoftMaxHeapSize must be less than or equal to the maximum heap size")
                    .shouldHaveExitValue(1);
    }
}
