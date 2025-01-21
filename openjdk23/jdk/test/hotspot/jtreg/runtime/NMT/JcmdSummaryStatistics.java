/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @summary Test the NMT scale parameter
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main/othervm -XX:NativeMemoryTracking=summary JcmdSummaryStatistics
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.JDKToolFinder;

public class JcmdSummaryStatistics {

    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder();
        OutputAnalyzer output;
        // Grab my own PID
        String pid = Long.toString(jdk.test.lib.process.ProcessTools.getProcessId());

        // Run 'jcmd <pid> VM.native_memory statistics=true'
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "statistics=true"});
        output = new OutputAnalyzer(pb.start());

        output.shouldContainMultiLinePattern(
                "Native Memory Tracking Statistics:",
                "State: summary",
                "Preinit state:",
                "entries:",
                "pre-init mallocs:",
                "MallocLimit:");
    }
}
