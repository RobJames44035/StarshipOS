/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test MallocSiteTypeChange
 * @bug 8200109
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:NativeMemoryTracking=detail MallocSiteTypeChange
 */

import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.whitebox.WhiteBox;

public class MallocSiteTypeChange {
    public static void main(String args[]) throws Exception {
        OutputAnalyzer output;
        WhiteBox wb = WhiteBox.getWhiteBox();

        // Grab my own PID
        String pid = Long.toString(ProcessTools.getProcessId());
        ProcessBuilder pb = new ProcessBuilder();

        int pc = 1;
        long addr = wb.NMTMallocWithPseudoStack(4 * 1024, pc);

        // Verify that current tracking level is "detail"
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "detail"});
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Test (reserved=4KB, committed=4KB)");

        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "baseline"});
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("Baseline taken");

        wb.NMTFree(addr);
        addr = wb.NMTMallocWithPseudoStackAndType(2 * 1024, pc, 9 /* mtInternal */ );
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "detail.diff"});
        output = new OutputAnalyzer(pb.start());
        output.shouldContain("(malloc=0KB type=Test -4KB)");
        output.shouldContain("(malloc=2KB type=Internal +2KB #1 +1)");
        output.shouldHaveExitValue(0);
  }
}
