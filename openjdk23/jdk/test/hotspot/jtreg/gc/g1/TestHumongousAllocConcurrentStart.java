/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestHumongousAllocConcurrentStart
 * @bug 7168848
 * @summary G1: humongous object allocations should initiate marking cycles when necessary
 * @requires vm.gc.G1
 * @requires vm.flagless
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.g1.TestHumongousAllocConcurrentStart
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestHumongousAllocConcurrentStart {
    // Heap sizes < 224 MB are increased to 224 MB if vm_page_size == 64K to
    // fulfill alignment constraints.
    private static final int heapSize                       = 224; // MB
    private static final int heapRegionSize                 = 1;   // MB
    private static final int initiatingHeapOccupancyPercent = 50;  // %

    public static void main(String[] args) throws Exception {
        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(
            "-XX:+UseG1GC",
            "-Xms" + heapSize + "m",
            "-Xmx" + heapSize + "m",
            "-XX:G1HeapRegionSize=" + heapRegionSize + "m",
            "-XX:InitiatingHeapOccupancyPercent=" + initiatingHeapOccupancyPercent,
            "-Xlog:gc",
            HumongousObjectAllocator.class.getName());

        output.shouldContain("Pause Young (Concurrent Start) (G1 Humongous Allocation)");
        output.shouldNotContain("Full GC");
        output.shouldHaveExitValue(0);
    }

    static class HumongousObjectAllocator {
        private static byte[] dummy;

        public static void main(String [] args) throws Exception {
            // Make object size 75% of region size
            final int humongousObjectSize =
                (int)(heapRegionSize * 1024 * 1024 * 0.75);

            // Number of objects to allocate to go above IHOP
            final int humongousObjectAllocations =
                (int)((heapSize * initiatingHeapOccupancyPercent / 100.0) / heapRegionSize) + 1;

            // Allocate
            for (int i = 1; i <= humongousObjectAllocations; i++) {
                System.out.println("Allocating humongous object " + i + "/" + humongousObjectAllocations +
                                   " of size " + humongousObjectSize + " bytes");
                dummy = new byte[humongousObjectSize];
            }
        }
    }
}
