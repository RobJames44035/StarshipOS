/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestParallelRefProc
 * @summary Test defaults processing for -XX:+ParallelRefProcEnabled.
 * @library /test/lib
 * @library /
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI gc.arguments.TestParallelRefProc
 */

import java.util.Arrays;
import java.util.ArrayList;

import jdk.test.lib.process.OutputAnalyzer;

import jtreg.SkippedException;
import jdk.test.whitebox.gc.GC;

public class TestParallelRefProc {

    public static void main(String args[]) throws Exception {
        boolean noneGCSupported = true;
        if (GC.Serial.isSupported()) {
            noneGCSupported = false;
            testFlag(new String[] { "-XX:+UseSerialGC" }, false);
        }
        if (GC.Parallel.isSupported()) {
            noneGCSupported = false;
            testFlag(new String[] { "-XX:+UseParallelGC", "-XX:ParallelGCThreads=1" }, false);
            testFlag(new String[] { "-XX:+UseParallelGC", "-XX:ParallelGCThreads=2" }, true);
            testFlag(new String[] { "-XX:+UseParallelGC", "-XX:-ParallelRefProcEnabled", "-XX:ParallelGCThreads=2" }, false);
        }
        if (GC.G1.isSupported()) {
            noneGCSupported = false;
            testFlag(new String[] { "-XX:+UseG1GC", "-XX:ParallelGCThreads=1" }, false);
            testFlag(new String[] { "-XX:+UseG1GC", "-XX:ParallelGCThreads=2" }, true);
            testFlag(new String[] { "-XX:+UseG1GC", "-XX:-ParallelRefProcEnabled", "-XX:ParallelGCThreads=2" }, false);
        }
        if (noneGCSupported) {
            throw new SkippedException("Skipping test because none of Serial/Parallel/G1 is supported.");
        }
    }

    private static final String parallelRefProcEnabledPattern =
        " *bool +ParallelRefProcEnabled *= *true +\\{product\\}";

    private static final String parallelRefProcDisabledPattern =
        " *bool +ParallelRefProcEnabled *= *false +\\{product\\}";

    private static void testFlag(String[] args, boolean expectedTrue) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(args));
        result.add("-XX:+PrintFlagsFinal");
        result.add("-version");
        OutputAnalyzer output = GCArguments.executeLimitedTestJava(result);

        output.shouldHaveExitValue(0);

        final String expectedPattern = expectedTrue ? parallelRefProcEnabledPattern : parallelRefProcDisabledPattern;

        String value = output.firstMatch(expectedPattern);
        if (value == null) {
            throw new RuntimeException(
                Arrays.toString(args) + " didn't set ParallelRefProcEnabled to " + expectedTrue + " as expected");
        }
    }
}
