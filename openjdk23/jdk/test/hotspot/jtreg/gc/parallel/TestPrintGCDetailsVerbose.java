/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package gc.parallel;

/*
 * @test TestPrintGCDetailsVerbose
 * @bug 8016740 8177963
 * @summary Tests that jvm with maximally verbose GC logging does not crash when ParOldGC has no memory
 * @requires vm.gc.Parallel
 * @modules java.base/jdk.internal.misc
 * @run main/othervm -Xmx50m -XX:+UseParallelGC -Xlog:gc*=trace gc.parallel.TestPrintGCDetailsVerbose
 */
public class TestPrintGCDetailsVerbose {

    public static void main(String[] args) {
        for (int t = 0; t <= 10; t++) {
            byte a[][] = new byte[100000][];
            try {
                for (int i = 0; i < a.length; i++) {
                    a[i] = new byte[100000];
                }
            } catch (OutOfMemoryError oome) {
                a = null;
                System.out.println("OOM!");
                continue;
            }
        }
    }
}
