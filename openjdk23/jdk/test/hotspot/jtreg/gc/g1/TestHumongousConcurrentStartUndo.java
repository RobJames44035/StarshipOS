/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestHumongousConcurrentStartUndo
 * @summary Tests an alternating sequence of Concurrent Mark and Concurrent Undo
 * cycles.
 * reclaim heap occupancy falls below the IHOP value.
 * @requires vm.gc.G1
 * @library /test/lib /testlibrary /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -Xbootclasspath/a:.
 *                   gc.g1.TestHumongousConcurrentStartUndo
 */

import gc.testlibrary.Helpers;

import jdk.test.whitebox.WhiteBox;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import java.lang.ref.Reference;

public class TestHumongousConcurrentStartUndo {
    // Heap sizes < 224 MB are increased to 224 MB if vm_page_size == 64K to
    // fulfill alignment constraints.
    private static final int HeapSize                       = 224; // MB
    private static final int HeapRegionSize                 = 1;   // MB
    private static final int InitiatingHeapOccupancyPercent = 50;  // %
    private static final int YoungSize                      = HeapSize / 8;

    public static void main(String[] args) throws Exception {
        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(
            "-Xbootclasspath/a:.",
            "-XX:+UseG1GC",
            "-Xms" + HeapSize + "m",
            "-Xmx" + HeapSize + "m",
            "-Xmn" + YoungSize + "m",
            "-XX:G1HeapRegionSize=" + HeapRegionSize + "m",
            "-XX:InitiatingHeapOccupancyPercent=" + InitiatingHeapOccupancyPercent,
            "-XX:-G1UseAdaptiveIHOP",
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+WhiteBoxAPI",
            "-Xlog:gc*",
            EdenObjectAllocatorWithHumongousAllocation.class.getName());

        output.shouldContain("Pause Young (Concurrent Start) (G1 Humongous Allocation)");
        output.shouldContain("Concurrent Undo Cycle");
        output.shouldContain("Concurrent Mark Cycle");
        output.shouldHaveExitValue(0);
        System.out.println(output.getStdout());
    }

    static class EdenObjectAllocatorWithHumongousAllocation {
        private static final WhiteBox WB = WhiteBox.getWhiteBox();

        private static final int M = 1024 * 1024;
        // Make humongous object size 75% of region size
        private static final int HumongousObjectSize =
                (int)(HeapRegionSize * M * 0.75);
        // Number of objects to allocate to go above IHOP
        private static final int NumHumongousObjectAllocations =
                (int)(((HeapSize - YoungSize) * 80 / 100.0) / HeapRegionSize);


        private static void allocateHumongous(int num, Object[] holder) {
            for (int i = 0; i < num; i++) {
                if (i % 10 == 0) {
                    System.out.println("Allocating humongous object " + i + "/" + num +
                                       " of size " + HumongousObjectSize + " bytes");
                }
                holder[i % holder.length] = new byte[HumongousObjectSize];
            }
        }

        private static void runConcurrentUndoCycle() {
            // Start from an "empty" heap.
            WB.fullGC();
            // The queue only holds one element, so only one humongous object
            // will be reachable and the concurrent operation should be undone.
            allocateHumongous(NumHumongousObjectAllocations, new Object[1]);
            Helpers.waitTillCMCFinished(WB, 1);
        }

        private static void runConcurrentMarkCycle() {
            Object[] a = new Object[NumHumongousObjectAllocations];
            // Start from an "empty" heap.
            WB.fullGC();
            // Try to trigger a concurrent mark cycle. Block concurrent operation
            // while we are allocating more humongous objects than the IHOP threshold.
            // After releasing control, trigger the full cycle.
            try {
                System.out.println("Acquire CM control");
                WB.concurrentGCAcquireControl();
                allocateHumongous(NumHumongousObjectAllocations, a);
            } finally {
                System.out.println("Release CM control");
                WB.concurrentGCReleaseControl();
            }
            // At this point we kept NumHumongousObjectAllocations humongous objects live
            // in "a" which is larger than the IHOP threshold. Another dummy humongous
            // allocation must trigger a concurrent cycle that is not an Undo Cycle.
            allocateHumongous(1, new Object[1]);
            Helpers.waitTillCMCFinished(WB, 1);

            Reference.reachabilityFence(a);
        }

        public static void main(String [] args) throws Exception {
            for (int iterate = 0; iterate < 3; iterate++) {
                runConcurrentUndoCycle();
                runConcurrentMarkCycle();
            }
        }
    }
}
