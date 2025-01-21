/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/* @test
 * @summary Check that TAMSes are correctly updated for regions dropped from
 *          the retained collection set candidates during a Concurrent Start pause.
 * @requires vm.gc.G1
 * @requires vm.flagless
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
                     -XX:+WhiteBoxAPI -Xbootclasspath/a:. -Xmx32m -XX:G1NumCollectionsKeepPinned=1
                     -XX:+VerifyBeforeGC -XX:+VerifyAfterGC -XX:G1MixedGCLiveThresholdPercent=100
                     -XX:G1HeapWastePercent=0 -Xlog:gc,gc+ergo+cset=trace gc.g1.pinnedobjs.TestDroppedRetainedTAMS
 */

package gc.g1.pinnedobjs;

import jdk.test.whitebox.WhiteBox;

public class TestDroppedRetainedTAMS {

    private static final WhiteBox wb = WhiteBox.getWhiteBox();

    private static final char[] dummy = new char[100];

    public static void main(String[] args) {
        wb.fullGC(); // Move the target dummy object to old gen.

        wb.pinObject(dummy);

        // After this concurrent cycle the pinned region will be in the the (marking)
        // collection set candidates.
        wb.g1RunConcurrentGC();

        // Pass the Prepare mixed gc which will not do anything about the marking
        // candidates.
        wb.youngGC();
        // Mixed GC. Will complete. That pinned region is now retained. The mixed gcs
        // will end here.
        wb.youngGC();

        // The pinned region will be dropped from the retained candidates during the
        // Concurrent Start GC, leaving that region's TAMS broken.
        wb.g1RunConcurrentGC();

        // Verification will find a lot of broken objects.
        wb.youngGC();
        System.out.println(dummy);
    }
}
