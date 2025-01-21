/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package gc.arguments;

/**
 * @test TestUseNUMAInterleaving
 * @summary Tests that UseNUMAInterleaving enabled for all collectors by
 * ergonomics, on all platforms when UseNUMA feature is enabled.
 * @bug 8059614
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.arguments.TestUseNUMAInterleaving
 */
import jdk.test.lib.process.OutputAnalyzer;

public class TestUseNUMAInterleaving {

    public static void main(String[] args) throws Exception {
        OutputAnalyzer output = GCArguments.executeTestJava(
            "-XX:+UseNUMA",
            "-XX:+PrintFlagsFinal",
            "-version");

        boolean isNUMAEnabled
                = Boolean.parseBoolean(output.firstMatch(NUMA_FLAG_PATTERN, 1));

        if (isNUMAEnabled) {
            output.shouldMatch("\\bUseNUMAInterleaving\\b.*?=.*?true");
            System.out.println(output.getStdout());
        } else {
            System.out.println(output.firstMatch(NUMA_FLAG_PATTERN));
            System.out.println(output.firstMatch(NUMA_FLAG_PATTERN, 1));
        }
    }

    private static final String NUMA_FLAG_PATTERN = "\\bUseNUMA\\b.*?=.*?([a-z]+)";
}
