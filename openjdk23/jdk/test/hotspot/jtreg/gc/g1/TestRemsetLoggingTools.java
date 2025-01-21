/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.g1;

/*
 * Common helpers for TestRemsetLogging* tests
 */

import jdk.test.whitebox.WhiteBox;

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import java.util.ArrayList;
import java.util.Arrays;

class VerifySummaryOutput {
    public static void main(String[] args) {
        int numGCs = Integer.parseInt(args[0]);

        // Perform the requested amount of GCs.
        WhiteBox wb = WhiteBox.getWhiteBox();
        for (int i = 0; i < numGCs - 1; i++) {
            wb.youngGC();
        }
        if (numGCs > 0) {
          wb.fullGC();
        }
    }
}

public class TestRemsetLoggingTools {

    public static String runTest(String[] additionalArgs, int numGCs) throws Exception {
        ArrayList<String> finalargs = new ArrayList<String>();
        String[] defaultArgs = new String[] {
            "-Xbootclasspath/a:.",
            "-XX:+UnlockDiagnosticVMOptions", "-XX:+WhiteBoxAPI",
            "-cp", System.getProperty("java.class.path"),
            "-XX:+UseG1GC",
            "-Xmn4m",
            "-Xint", // -Xint makes the test run faster
            "-Xms20m",
            "-Xmx20m",
            "-XX:ParallelGCThreads=1",
            "-XX:InitiatingHeapOccupancyPercent=100", // we don't want the additional GCs due to marking
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:G1HeapRegionSize=1M",
        };

        finalargs.addAll(Arrays.asList(defaultArgs));

        if (additionalArgs != null) {
            finalargs.addAll(Arrays.asList(additionalArgs));
        }

        finalargs.add(VerifySummaryOutput.class.getName());
        finalargs.add(String.valueOf(numGCs));

        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(finalargs);

        output.shouldHaveExitValue(0);

        String result = output.getStdout();
        return result;
    }

    private static void checkCounts(int expected, int actual, String which) throws Exception {
        if (expected != actual) {
            throw new Exception("RSet summaries mention " + which + " regions an incorrect number of times. Expected " + expected + ", got " + actual);
        }
    }

    public static void expectPerRegionRSetSummaries(String result, int expectedCumulative, int expectedPeriodic) throws Exception {
        expectRSetSummaries(result, expectedCumulative, expectedPeriodic);
        int actualYoung = result.split("Young regions").length - 1;
        int actualHumongous = result.split("Humongous regions").length - 1;
        int actualFree = result.split("Free regions").length - 1;
        int actualOther = result.split("Old regions").length - 1;

        // the strings we check for above are printed four times per summary
        int expectedPerRegionTypeInfo = (expectedCumulative + expectedPeriodic) * 4;

        checkCounts(expectedPerRegionTypeInfo, actualYoung, "Young");
        checkCounts(expectedPerRegionTypeInfo, actualHumongous, "Humongous");
        checkCounts(expectedPerRegionTypeInfo, actualFree, "Free");
        checkCounts(expectedPerRegionTypeInfo, actualOther, "Old");
    }

    public static void expectRSetSummaries(String result, int expectedCumulative, int expectedPeriodic) throws Exception {
        int actualTotal = result.split("Current rem set statistics").length - 1;
        int actualCumulative = result.split("Cumulative RS summary").length - 1;

        if (expectedCumulative != actualCumulative) {
            throw new Exception("Incorrect amount of RSet summaries at the end. Expected " + expectedCumulative + ", got " + actualCumulative);
        }

        if (expectedPeriodic != (actualTotal - actualCumulative)) {
            throw new Exception("Incorrect amount of per-period RSet summaries at the end. Expected " + expectedPeriodic + ", got " + (actualTotal - actualCumulative));
        }
    }
}
