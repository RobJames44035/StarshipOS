/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary Test consistency of NMT by creating allocations of the Test type with various sizes and verifying visibility with jcmd
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:NativeMemoryTracking=detail MallocRoundingReportTest
 *
 */

import jdk.test.whitebox.WhiteBox;

public class MallocRoundingReportTest {
    private static long K = 1024;

    public static void main(String args[]) throws Exception {
        WhiteBox wb = WhiteBox.getWhiteBox();

        long[] additionalBytes = {0, 1, 512, 650};
        long[] kByteSize = {1024, 2048};
        long mallocd_total = 0;
        for ( int i = 0; i < kByteSize.length; i++)
        {
            for (int j = 0; j < (additionalBytes.length); j++) {
                long curKB = kByteSize[i] + additionalBytes[j];
                // round up/down to the nearest KB to match NMT reporting
                long numKB = (curKB % kByteSize[i] >= 512) ? ((curKB / K) + 1) : curKB / K;
                // Use WB API to alloc and free with the mtTest type
                mallocd_total = wb.NMTMalloc(curKB);
                // Run 'jcmd <pid> VM.native_memory summary', check for expected output
                // NMT does not track memory allocations less than 1KB, and rounds to the nearest KB
                NMTTestUtils.runJcmdSummaryReportAndCheckOutput(
                        "Test (reserved=" + numKB + "KB, committed=" + numKB + "KB)",
                        "(malloc=" + numKB + "KB #1) (at peak)"
                );

                wb.NMTFree(mallocd_total);

                // Run 'jcmd <pid> VM.native_memory summary', check for expected output
                NMTTestUtils.runJcmdSummaryReportAndCheckOutput(
                        "Test (reserved=0KB, committed=0KB)",
                        "(malloc=0KB) (peak=" + numKB + "KB #1)"
                );
            }
        }
    }
}
