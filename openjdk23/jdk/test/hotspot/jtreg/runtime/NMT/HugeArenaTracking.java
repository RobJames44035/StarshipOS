/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @library /test/lib
 * @requires vm.bits == 64
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:NativeMemoryTracking=summary HugeArenaTracking
 */

import java.util.Random;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.Utils;
import jdk.test.whitebox.WhiteBox;

public class HugeArenaTracking {
  private static final long MB = 1024 * 1024;
  private static final long GB = MB * 1024;

  public static void main(String args[]) throws Exception {
    final WhiteBox wb = WhiteBox.getWhiteBox();

    long arena1 = wb.NMTNewArena(1024);
    long arena2 = wb.NMTNewArena(1024);

    NMTTestUtils.runJcmdSummaryReportAndCheckOutput(
            new String[] { "scale=K" },
            new String[] { "Test (reserved=2KB, committed=2KB)",
                           "(arena=2KB #2) (at peak)" });

    Random rand = Utils.getRandomInstance();

    // Allocate 2GB+ from arena
    long total = 0;
    while (total < 2 * GB) {
      wb.NMTArenaMalloc(arena1, MB);
      total += MB;
    }

    // run a report at GB level. We should see our allocations; since they are rounded
    // to GB, we expect an exact output match
    NMTTestUtils.runJcmdSummaryReportAndCheckOutput(
            new String[] { "scale=G" },
            new String[] { "Test (reserved=2GB, committed=2GB)",
                           "(arena=2GB #2) (at peak)" });

    // Repeat at MB level; we expect the same behavior
    NMTTestUtils.runJcmdSummaryReportAndCheckOutput(
            new String[] { "scale=M" },
            new String[] { "Test (reserved=2048MB, committed=2048MB)",
                           "(arena=2048MB #2) (at peak)" });

    wb.NMTFreeArena(arena1);

    // Repeat report at GB level. Reserved should be 0 now. Current usage is 1KB, since arena2 is left, but that
    // is below GB scale threshold, so should show up as 0.
    NMTTestUtils.runJcmdSummaryReportAndCheckOutput(
            new String[] { "scale=G" },
            new String[] { "Test (reserved=0GB, committed=0GB)",
                           "(arena=0GB #1) (peak=2GB #2)" });

    // Same, for MB scale
    NMTTestUtils.runJcmdSummaryReportAndCheckOutput(
            new String[] { "scale=M" },
            new String[] { "Test (reserved=0MB, committed=0MB)",
                           "(arena=0MB #1) (peak=2048MB #2)" });

    // At KB level we should see the remaining 1KB. Note that we refrain from testing peak here
    // since the number gets fuzzy: it depends on the size of the initially allocated chunk. At MB
    // and GB scale, these differences don't matter.
    NMTTestUtils.runJcmdSummaryReportAndCheckOutput(
            new String[] { "scale=K" },
            new String[] { "Test (reserved=1KB, committed=1KB)",
                           "(arena=1KB #1) (peak=" });

    wb.NMTFreeArena(arena2);

    // Everything free'd, current usage 0, peak should be preserved.
    NMTTestUtils.runJcmdSummaryReportAndCheckOutput(
            new String[] { "scale=G" },
            new String[] { "Test (reserved=0GB, committed=0GB)",
                           "(arena=0GB #0) (peak=2GB #2)" });
  }
}
