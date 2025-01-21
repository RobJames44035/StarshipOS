/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.stress.gcold;

/*
 * @test TestGCOldWithG1
 * @key randomness
 * @library / /test/lib
 * @requires vm.gc.G1
 * @summary Stress the G1 GC by trying to make old objects more likely to be garbage than young objects.
 * @run main/othervm -Xmx384M -XX:+UseG1GC gc.stress.gcold.TestGCOldWithG1 50 1 20 10 10000
 * @run main/othervm -Xms64m -Xmx128m -XX:+UseG1GC -XX:+UseDynamicNumberOfGCThreads -Xlog:gc,gc+task=trace gc.stress.gcold.TestGCOldWithG1 50 5 20 1 5000
 * @run main/othervm -Xms64m -Xmx128m -XX:+UseG1GC -XX:+UseDynamicNumberOfGCThreads  -XX:+UnlockDiagnosticVMOptions -XX:+InjectGCWorkerCreationFailure -Xlog:gc,gc+task=trace gc.stress.gcold.TestGCOldWithG1 50 5 20 1 5000
 */
public class TestGCOldWithG1 {
    public static void main(String[] args) {
        TestGCOld.main(args);
    }
}
