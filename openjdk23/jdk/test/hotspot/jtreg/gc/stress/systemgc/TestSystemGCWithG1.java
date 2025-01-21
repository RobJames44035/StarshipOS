/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.stress.systemgc;

/*
 * @test TestSystemGCWithG1
 * @key stress
 * @bug 8190703
 * @library /
 * @requires vm.gc.G1
 * @summary Stress the G1 GC full GC by allocating objects of different lifetimes concurrently with System.gc().
 * @run main/othervm/timeout=300 -Xlog:gc*=info -Xmx512m -XX:+UseG1GC gc.stress.systemgc.TestSystemGCWithG1 270
 */
public class TestSystemGCWithG1 {
    public static void main(String[] args) throws Exception {
        TestSystemGC.main(args);
    }
}
