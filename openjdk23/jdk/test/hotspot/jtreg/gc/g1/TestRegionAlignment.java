/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.g1;

/*
 * @test TestRegionAlignment.java
 * @bug 8013791
 * @requires vm.gc.G1
 * @summary Make sure that G1 ergonomics pick a heap size that is aligned with the region size
 * @run main/othervm -XX:+UseG1GC -XX:G1HeapRegionSize=32m -XX:MaxRAM=555m gc.g1.TestRegionAlignment
 *
 * When G1 ergonomically picks a maximum heap size it must be aligned to the region size.
 * This test tries to get the VM to pick a small and unaligned heap size (by using MaxRAM=555) and a
 * large region size (by using -XX:G1HeapRegionSize=32m). This will fail without the fix for 8013791.
 */
public class TestRegionAlignment {
    public static void main(String[] args) { }
}
