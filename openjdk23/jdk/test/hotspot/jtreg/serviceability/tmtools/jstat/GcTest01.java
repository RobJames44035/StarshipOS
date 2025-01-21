/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test checks output displayed with jstat -gc.
 *          Test scenario:
 *          test several times provokes garbage collection
 *          in the debuggee application
 *          and after each garbage collection runs jstat.
 *          jstat should show that after garbage collection
 *          number of GC events and garbage
 *          collection time increase.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library ../share
 * @requires vm.opt.ExplicitGCInvokesConcurrent != true
 * @requires vm.gc != "Z" & vm.gc != "Shenandoah"
 * @run main/othervm -XX:+UsePerfData -Xmx128M GcTest01
 */
import utils.*;

public class GcTest01 {

    public static void main(String[] args) throws Exception {

        // We will be running "jstat -gc" tool
        JstatGcTool jstatGcTool = new JstatGcTool(ProcessHandle.current().pid());

        // Run once and get the  results asserting that they are reasonable
        JstatGcResults measurement1 = jstatGcTool.measure();
        measurement1.assertConsistency();

        GcProvoker gcProvoker = new GcProvoker();

        // Provoke GC then run the tool again and get the results
        // asserting that they are reasonable
        gcProvoker.provokeGc();
        JstatGcResults measurement2 = jstatGcTool.measure();
        measurement2.assertConsistency();

        // Assert the increase in GC events and time between the measurements
        JstatResults.assertGCEventsIncreased(measurement1, measurement2);
        JstatResults.assertGCTimeIncreased(measurement1, measurement2);

        // Provoke GC again and get the results
        // asserting that they are reasonable
        gcProvoker.provokeGc();
        JstatGcResults measurement3 = jstatGcTool.measure();
        measurement3.assertConsistency();

        // Assert the increase in GC events and time between the measurements
        JstatResults.assertGCEventsIncreased(measurement2, measurement3);
        JstatResults.assertGCTimeIncreased(measurement2, measurement3);

    }

}
