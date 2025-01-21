/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Test that os::attempt_reserve_memory_at doesn't register the memory as committed
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:NativeMemoryTracking=detail VirtualAllocAttemptReserveMemoryAt
 *
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.JDKToolFinder;

import jdk.test.whitebox.WhiteBox;

import static jdk.test.lib.Asserts.*;

public class VirtualAllocAttemptReserveMemoryAt {

    public static WhiteBox wb = WhiteBox.getWhiteBox();

    public static void main(String args[]) throws Exception {
        long reserveSize = 4 * 1024 * 1024; // 4096KB

        String pid = Long.toString(ProcessTools.getProcessId());
        ProcessBuilder pb = new ProcessBuilder();

        // Find an address
        long addr = wb.NMTReserveMemory(reserveSize);

        // Release it
        wb.NMTReleaseMemory(addr, reserveSize);

        long attempt_addr = wb.NMTAttemptReserveMemoryAt(addr, reserveSize);

        if (attempt_addr == 0) {
            // We didn't manage ot get the requested memory address.
            // It's not necessarily a bug, so giving up.
            return;
        }

        assertEQ(addr, attempt_addr);

        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid,
                "VM.native_memory", "detail" });

        OutputAnalyzer output = new OutputAnalyzer(pb.start());

        output.shouldContain("Test (reserved=4096KB, committed=0KB)");

        wb.NMTReleaseMemory(addr, reserveSize);
        output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("Test (reserved=");
        output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr) + " - 0x[0]*"
                + Long.toHexString(addr + reserveSize) + "\\] reserved 4096KB for Test");
    }
}
