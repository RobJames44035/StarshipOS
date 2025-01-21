/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package gc.metaspace;

/*
 * @test
 * @bug 8049831
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @requires vm.bits == 32
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI gc.metaspace.TestCapacityUntilGCWrapAround
 */

import jdk.test.whitebox.WhiteBox;

import jdk.test.lib.Asserts;

public class TestCapacityUntilGCWrapAround {
    private static long MB = 1024 * 1024;
    private static long GB = 1024 * MB;
    private static long MAX_UINT = 4 * GB - 1; // On 32-bit platforms

    public static void main(String[] args) {
        WhiteBox wb = WhiteBox.getWhiteBox();

        long before = wb.metaspaceCapacityUntilGC();
        // Now force possible overflow of capacity_until_GC.
        long after = wb.incMetaspaceCapacityUntilGC(MAX_UINT);

        Asserts.assertGTE(after, before,
                          "Increasing with MAX_UINT should not cause wrap around: " + after + " < " + before);
        Asserts.assertLTE(after, MAX_UINT,
                          "Increasing with MAX_UINT should not cause value larger than MAX_UINT:" + after);
    }
}
