/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary Verify that jcmd correctly reports that baseline succeeds with NMT enabled with detailed tracking.
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main/othervm -XX:NativeMemoryTracking=detail JcmdBaselineDetail
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.JDKToolFinder;

public class JcmdBaselineDetail {

    public static void main(String args[]) throws Exception {
        // Grab my own PID
        String pid = Long.toString(ProcessTools.getProcessId());
        OutputAnalyzer output;

        ProcessBuilder pb = new ProcessBuilder();

        // Run 'jcmd <pid> VM.native_memory baseline=true'
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "baseline=true"});

        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Baseline taken");
    }
}
