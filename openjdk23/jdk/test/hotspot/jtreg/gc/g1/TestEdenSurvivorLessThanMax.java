/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestEdenSurvivorLessThanMax
 * @bug 8152724
 * @summary Check that G1 eden plus survivor max capacity after GC does not exceed maximum number of regions.
 * @requires vm.gc.G1
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xlog:gc+heap=debug -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UseG1GC -Xmx64M -Xms64M -Xmn50M -XX:SurvivorRatio=1 gc.g1.TestEdenSurvivorLessThanMax
 */

import jdk.test.whitebox.WhiteBox;

// The test fills the heap in a way that previous to 8152724 the maximum number of survivor regions
// for that young gc was higher than there was free space left which is impossible.
public class TestEdenSurvivorLessThanMax {
    private static final long BYTES_TO_FILL = 50 * 1024 * 1024;

    private static final WhiteBox WB = WhiteBox.getWhiteBox();

    public static void main(String[] args) throws Exception {
        Object o = new int[100];

        long objSize = WB.getObjectSize(o);

        // Fill eden to approximately ~90%.
        int numObjs = (int)((BYTES_TO_FILL / objSize) * 9 / 10);
        for (int i = 0; i < numObjs; i++) {
          o = new int[100];
        }

        WB.youngGC();

        System.out.println(o.toString());
    }
}
