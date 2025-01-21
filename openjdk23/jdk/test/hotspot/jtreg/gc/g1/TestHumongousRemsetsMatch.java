/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestHumongousRemSetsMatch
 * @bug 8205426
 * @summary Test to make sure that humongous object remset states are in sync
 * @requires vm.gc.G1 & os.maxMemory >= 2G
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -Xmx512M -Xms512M -Xmn10M -XX:ParallelGCThreads=2 -XX:-UseDynamicNumberOfGCThreads -XX:+UseG1GC -XX:+WhiteBoxAPI -XX:G1HeapRegionSize=1M -XX:+VerifyAfterGC -Xlog:gc,gc+remset+tracking=trace gc.g1.TestHumongousRemsetsMatch
 */

import jdk.test.whitebox.WhiteBox;

public class TestHumongousRemsetsMatch {

    // G1 at the moment uses one thread every this amount of regions.
    private static final int WorkerThreadBoundary = 384;

    private static final int ObjSizeInRegions = 17;
    private static final int M = 1024 * 1024;
    private static final int TypeArrayObjSize = ObjSizeInRegions * M / 4 /* sizeof(int) */ - 1024 /* > header size */;

    public static void main(String[] args) throws Exception {
        WhiteBox wb = WhiteBox.getWhiteBox();

        for (int j = 0; j < 3; j++) {
            wb.fullGC(); // Start with a clean slate

            // It may happen that our 7-region sized humongous objects may just be "misaligned"
            // so that they do not cross the region 384 boundary. Try to counter this by offsetting
            // the humongous objects just a little.
            Object alignmentFudge = new int[(j + 1) * M / 4 /* sizeof(int) */ - 1024];

            // Fill the heap so that more than WorkerThreadBoundary regions are occupied with humongous objects
            // and hopefully one of these objects crosses the region WorkerThreadBoundary boundary.
            Object[] lotsOfHumongousObjects = new Object[(WorkerThreadBoundary / ObjSizeInRegions) + 3];

            for (int i = 0; i < lotsOfHumongousObjects.length; i++) {
                lotsOfHumongousObjects[i] = new int[TypeArrayObjSize];
            }

            wb.fullGC();
            wb.g1RunConcurrentGC();
            wb.youngGC(); // Trigger verification error.

            System.out.println(lotsOfHumongousObjects + " " + alignmentFudge);
        }
    }
}
