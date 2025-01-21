/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary run NMT baseline, create threads and verify output from summary.diff
 * @author Evgeny Ignatenko
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary SummaryDiffThreadCount
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.JDKToolFinder;

public class SummaryDiffThreadCount {
    public static void main(String args[]) throws Exception {
        ProcessBuilder pb = new ProcessBuilder();
        OutputAnalyzer output;
        // Grab my own PID.
        String pid = Long.toString(ProcessTools.getProcessId());

        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "baseline=true"});
        pb.start().waitFor();

        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Baseline taken");

        // Creating 10 threads.
        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                while (true) { continue; }
            }).start();
        }

        // Running "jcmd <pid> VM.native_memory summary.diff" and checking for five new threads reported.
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "summary.diff"});
        output = new OutputAnalyzer(pb.start());

        // Trailing '+' is needed to check that NMT now reports that now we have more threads than it
        // was during the baseline.
        output.shouldMatch("threads #\\d+ \\+");
    }
}
