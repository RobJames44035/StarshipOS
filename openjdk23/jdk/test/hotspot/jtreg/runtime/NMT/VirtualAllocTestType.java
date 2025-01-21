/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @summary Test Reserve/Commit/Uncommit/Release of virtual memory and that we track it correctly
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:NativeMemoryTracking=detail VirtualAllocTestType
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.whitebox.WhiteBox;

public class VirtualAllocTestType {

  public static WhiteBox wb = WhiteBox.getWhiteBox();
  public static void main(String args[]) throws Exception {
    OutputAnalyzer output;
    long commitSize = 128 * 1024;
    long reserveSize = 256 * 1024;
    long addr1, addr2;

    String info = "start";

    try {
      // ------
      // Reserve first mapping
      addr1 = wb.NMTReserveMemory(reserveSize);
      info = "reserve 1: addr1=" + addr1;

      output = NMTTestUtils.startJcmdVMNativeMemoryDetail();
      checkReservedCommittedSummary(output, 256, 0);
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + reserveSize) + "\\] reserved 256KB for Test");

      // ------
      // Reserve second mapping
      addr2 = wb.NMTReserveMemory(reserveSize);
      info = "reserve 2: addr2=" + addr2;

      // If the second mapping happens to be adjacent to the first mapping, reserve another mapping and release the second mapping; for
      // this test, we want to see two disjunct mappings.
      if (addr2 == addr1 + reserveSize) {
        long tmp = wb.NMTReserveMemory(reserveSize);
        wb.NMTReleaseMemory(addr2, reserveSize);
        addr2 = tmp;
      }

      output = NMTTestUtils.startJcmdVMNativeMemoryDetail();
      checkReservedCommittedSummary(output, 512, 0);
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + reserveSize) + "\\] reserved 256KB for Test");

      // ------
      // Now commit the first mapping
      wb.NMTCommitMemory(addr1, commitSize);
      info = "commit 1";

      output = NMTTestUtils.startJcmdVMNativeMemoryDetail();
      checkReservedCommittedSummary(output, 512, 128);
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + commitSize) + "\\] committed 128KB");
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + reserveSize) + "\\] reserved 256KB for Test");

      // ------
      // Now commit the second mapping
      wb.NMTCommitMemory(addr2, commitSize);
      info = "commit 2";

      output = NMTTestUtils.startJcmdVMNativeMemoryDetail();
      checkReservedCommittedSummary(output, 512, 256);
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + commitSize) + "\\] committed 128KB");
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + commitSize) + "\\] committed 128KB");

      // ------
      // Now uncommit the second mapping
      wb.NMTUncommitMemory(addr2, commitSize);
      info = "uncommit 2";

      output = NMTTestUtils.startJcmdVMNativeMemoryDetail();
      checkReservedCommittedSummary(output, 512, 128);
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + commitSize) + "\\] committed 128KB");
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + commitSize) + "\\] committed 128KB");

      // ------
      // Now uncommit the first mapping
      wb.NMTUncommitMemory(addr1, commitSize);
      info = "uncommit 1";

      output = NMTTestUtils.startJcmdVMNativeMemoryDetail();
      checkReservedCommittedSummary(output, 512, 0);
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + commitSize) + "\\] committed 128KB");
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + commitSize) + "\\] committed 128KB");

      // ----------
      // Release second mapping
      wb.NMTReleaseMemory(addr2, reserveSize);
      info = "release 2";

      output = NMTTestUtils.startJcmdVMNativeMemoryDetail();
      checkReservedCommittedSummary(output, 256, 0);
      output.shouldMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + commitSize) + "\\] committed 128KB");
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + commitSize) + "\\] committed 128KB");

      // ----------
      // Release first mapping
      wb.NMTReleaseMemory(addr1, reserveSize);
      info = "release 1";

      output = NMTTestUtils.startJcmdVMNativeMemoryDetail();
      checkReservedCommittedSummary(output, 0, 0);
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr1) + " - 0x[0]*" + Long.toHexString(addr1 + commitSize) + "\\] committed 128KB");
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + reserveSize) + "\\] reserved 256KB for Test");
      output.shouldNotMatch("\\[0x[0]*" + Long.toHexString(addr2) + " - 0x[0]*" + Long.toHexString(addr2 + commitSize) + "\\] committed 128KB");

    } catch (Exception e) {
      throw new RuntimeException(e.getMessage() + " (" + info + ")");
    }
  }

  static long peakKB = 0;

  public static void checkReservedCommittedSummary(OutputAnalyzer output, long reservedKB, long committedKB) {
    if (committedKB > peakKB) {
      peakKB = committedKB;
    }
    NMTTestUtils.checkReservedCommittedSummary(output, reservedKB, committedKB, peakKB);
  }
}
