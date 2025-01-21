/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestRemsetLogging.java
 * @requires vm.gc.G1
 * @bug 8013895 8129977 8145534
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management/sun.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @summary Verify output of -Xlog:gc+remset*=trace
 * @run driver gc.g1.TestRemsetLogging
 *
 * Test the output of -Xlog:gc+remset*=trace in conjunction with G1SummarizeRSetStatsPeriod.
 */

public class TestRemsetLogging {

    public static void main(String[] args) throws Exception {
        String result;

        // no remembered set summary output
        result = TestRemsetLoggingTools.runTest(null, 0);
        TestRemsetLoggingTools.expectRSetSummaries(result, 0, 0);

        // no remembered set summary output
        result = TestRemsetLoggingTools.runTest(null, 2);
        TestRemsetLoggingTools.expectRSetSummaries(result, 0, 0);

        // no remembered set summary output
        result = TestRemsetLoggingTools.runTest(new String[] { "-XX:G1SummarizeRSetStatsPeriod=1" }, 3);
        TestRemsetLoggingTools.expectRSetSummaries(result, 0, 0);

        // single remembered set summary output at the end
        result = TestRemsetLoggingTools.runTest(new String[] { "-Xlog:gc+remset*=trace" }, 0);
        TestRemsetLoggingTools.expectRSetSummaries(result, 1, 0);

        // single remembered set summary output at the end
        result = TestRemsetLoggingTools.runTest(new String[] { "-Xlog:gc+remset*=trace" }, 2);
        TestRemsetLoggingTools.expectRSetSummaries(result, 1, 0);

        // single remembered set summary output
        result = TestRemsetLoggingTools.runTest(new String[] { "-Xlog:gc+remset*=trace", "-XX:G1SummarizeRSetStatsPeriod=1" }, 0);
        TestRemsetLoggingTools.expectRSetSummaries(result, 1, 0);

        // two times remembered set summary output
        result = TestRemsetLoggingTools.runTest(new String[] { "-Xlog:gc+remset*=trace", "-XX:G1SummarizeRSetStatsPeriod=1" }, 1);
        TestRemsetLoggingTools.expectRSetSummaries(result, 1, 2);

        // four times remembered set summary output
        result = TestRemsetLoggingTools.runTest(new String[] { "-Xlog:gc+remset*=trace", "-XX:G1SummarizeRSetStatsPeriod=1" }, 3);
        TestRemsetLoggingTools.expectRSetSummaries(result, 1, 6);

        // three times remembered set summary output
        result = TestRemsetLoggingTools.runTest(new String[] { "-Xlog:gc+remset*=trace", "-XX:G1SummarizeRSetStatsPeriod=2" }, 3);
        TestRemsetLoggingTools.expectRSetSummaries(result, 1, 4);

        // single remembered set summary output
        result = TestRemsetLoggingTools.runTest(new String[] { "-Xlog:gc+remset*=trace", "-XX:G1SummarizeRSetStatsPeriod=100" }, 3);
        TestRemsetLoggingTools.expectRSetSummaries(result, 1, 2);
    }
}
