/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8332125
 * @summary Test to verify correctness of total malloc and mmap diffs
 * @key randomness
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:TieredStopAtLevel=1 -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:NativeMemoryTracking=summary -Xms32m -Xmx32m TotalMallocMmapDiffTest
 *
 */

import jdk.test.lib.process.OutputAnalyzer;

import jdk.test.whitebox.WhiteBox;

public class TotalMallocMmapDiffTest {
    private static final WhiteBox wb = WhiteBox.getWhiteBox();
    private static final long ALLOCATE_SIZE = 250 * 1024 * 1024; // 250MB
    private static final double FUDGE_FACTOR_UPPER = 0.3;
    private static final double FUDGE_FACTOR_LOWER = 0.2;
    private static final double UPPER_BOUND = ALLOCATE_SIZE * (1 + FUDGE_FACTOR_UPPER);
    private static final double LOWER_BOUND = ALLOCATE_SIZE * (1 - FUDGE_FACTOR_LOWER);

    public static void main(String[] args) throws Exception {

        // Get baseline
        OutputAnalyzer output = NMTTestUtils.startJcmdVMNativeMemory("baseline=true", "scale=1");
        output.shouldContain("Baseline taken");

        // Allocate some memory via malloc
        long addr = wb.NMTMalloc(ALLOCATE_SIZE);

        // Virtually reserve and commit memory
        addr = wb.NMTReserveMemory(ALLOCATE_SIZE);
        wb.NMTCommitMemory(addr, ALLOCATE_SIZE);

        // Get NMT diff
        output = NMTTestUtils.startJcmdVMNativeMemory("summary.diff", "scale=1");

        // Verify malloc diff accounts for memory allocation with a fudge factor
        long mallocDiff = getMallocDiff(output);
        if (mallocDiff < LOWER_BOUND || mallocDiff > UPPER_BOUND) {
            throw new Exception("Total malloc diff is incorrect. " +
                    "Expected malloc diff range: [" + LOWER_BOUND + " - " + UPPER_BOUND + "]" +
                    "Actual malloc diff: " + mallocDiff);
        }

        // Verify mmap diff accounts for reserve and commit
        long reservedDiff = getReservedDiff(output);
        long committedDiff = getCommittedDiff(output);
        if (reservedDiff < LOWER_BOUND || reservedDiff > UPPER_BOUND) {
            throw new Exception("mmap reserved diff is incorrect. " +
                    "Expected reserved diff range: [" + LOWER_BOUND + " - " + UPPER_BOUND + "]" +
                    "Actual reserved diff: " + reservedDiff);
        }
        if (committedDiff < LOWER_BOUND || committedDiff > UPPER_BOUND) {
            throw new Exception("mmap committed diff is incorrect. " +
                    "Expected committed diff range: [" + LOWER_BOUND + " - " + UPPER_BOUND + "]" +
                    "Actual committed diff: " + committedDiff);
        }

    }

    private static long getMallocDiff(OutputAnalyzer output) {
        // First match should be global malloc diff
        String malloc = output.firstMatch("malloc=\\d+ \\+(\\d+)", 1);
        return Long.parseLong(malloc);
    }

    private static long getReservedDiff(OutputAnalyzer output) {
        // First match should be global mmap diff
        String reservedDiff = output.firstMatch("mmap: reserved=\\d+ \\+(\\d+)", 1);
        return Long.parseLong(reservedDiff);
    }

    private static long getCommittedDiff(OutputAnalyzer output) {
        // First match should be global mmap diff
        String committedDiff = output.firstMatch("mmap: reserved=\\d+ \\+\\d+, committed=\\d+ \\+(\\d+)", 1);
        return Long.parseLong(committedDiff);
    }
}
