/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary run NMT baseline, allocate memory and verify output from summary.diff
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:NativeMemoryTracking=summary JcmdSummaryDiff
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.JDKToolFinder;

import jdk.test.whitebox.WhiteBox;

public class JcmdSummaryDiff {

    public static WhiteBox wb = WhiteBox.getWhiteBox();

    public static void main(String args[]) throws Exception {
        ProcessBuilder pb = new ProcessBuilder();
        OutputAnalyzer output;
        // Grab my own PID
        String pid = Long.toString(ProcessTools.getProcessId());

        long commitSize = 128 * 1024;
        long reserveSize = 256 * 1024;
        long addr;

        // Run 'jcmd <pid> VM.native_memory baseline=true'
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "baseline=true"});

        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Baseline taken");

        addr = wb.NMTReserveMemory(reserveSize);
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "summary.diff", "scale=KB"});

        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Test (reserved=256KB +256KB, committed=0KB)");

        wb.NMTCommitMemory(addr, commitSize);
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "summary.diff", "scale=KB"});

        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Test (reserved=256KB +256KB, committed=128KB +128KB)");

        wb.NMTUncommitMemory(addr, commitSize);
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "summary.diff", "scale=KB"});

        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Test (reserved=256KB +256KB, committed=0KB)");

        wb.NMTReleaseMemory(addr, reserveSize);
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "summary.diff", "scale=KB"});

        output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("Test (reserved=");
    }
}
