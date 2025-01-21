/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package gc.g1;

import static gc.testlibrary.Allocation.blackHole;

/*
 * @test TestHumongousAllocNearlyFullRegion
 * @bug 8143587
 * @summary G1: humongous object allocations should work even when there is
 *              not enough space in the G1HeapRegion to fit a filler object.
 * @requires vm.gc.G1
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library /
 * @run driver gc.g1.TestHumongousAllocNearlyFullRegion
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestHumongousAllocNearlyFullRegion {
    // Heap sizes < 224 MB are increased to 224 MB if vm_page_size == 64K to
    // fulfill alignment constraints.
    private static final int heapSize                       = 224; // MB
    private static final int heapRegionSize                 = 1;   // MB

    public static void main(String[] args) throws Exception {
        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(
            "-XX:+UseG1GC",
            "-Xms" + heapSize + "m",
            "-Xmx" + heapSize + "m",
            "-XX:G1HeapRegionSize=" + heapRegionSize + "m",
            "-Xlog:gc",
            HumongousObjectAllocator.class.getName());

        output.shouldContain("Pause Young (Concurrent Start) (G1 Humongous Allocation)");
        output.shouldHaveExitValue(0);
    }

    static class HumongousObjectAllocator {
        public static void main(String [] args) {
            for (int i = 0; i < heapSize; i++) {
                // 131069 is the number of longs it takes to fill a G1HeapRegion except
                // for 8 bytes on 64 bit.
                blackHole(new long[131069]);
            }
        }
    }
}
