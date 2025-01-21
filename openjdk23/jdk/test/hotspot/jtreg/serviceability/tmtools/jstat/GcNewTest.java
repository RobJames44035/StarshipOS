/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import utils.*;
/*
 * @test
 * @summary Test checks output displayed with jstat -gcnew.
 *          Test scenario:
 *          test several times provokes garbage collection in the debuggee application and after each garbage
 *          collection runs jstat. jstat should show that after garbage collection number of GC events and garbage
 *          collection time increase.
 * @requires vm.gc != "Z" & vm.gc != "Shenandoah"
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library ../share
 * @run main/othervm -XX:+UsePerfData -Xmx128M GcNewTest
 */

public class GcNewTest {

    public static void main(String[] args) throws Exception {

        // We will be running "jstat -gc" tool
        JstatGcNewTool jstatGcTool = new JstatGcNewTool(ProcessHandle.current().pid());

        // Run once and get the  results asserting that they are reasonable
        JstatGcNewResults measurement1 = jstatGcTool.measure();
        measurement1.assertConsistency();

        GcProvoker gcProvoker = new GcProvoker();

        // Provoke GC and run the tool again
        gcProvoker.provokeGc();
        JstatGcNewResults measurement2 = jstatGcTool.measure();
        measurement2.assertConsistency();

        // Assert the increase in GC events and time between the measurements
        assertThat(measurement2.getFloatValue("YGC") > measurement1.getFloatValue("YGC"), "YGC didn't increase between measurements 1 and 2");
        assertThat(measurement2.getFloatValue("YGCT") > measurement1.getFloatValue("YGCT"), "YGCT time didn't increase between measurements 1 and 2");

        // Provoke GC and run the tool again
        gcProvoker.provokeGc();
        JstatGcNewResults measurement3 = jstatGcTool.measure();
        measurement3.assertConsistency();

        // Assert the increase in GC events and time between the measurements
        assertThat(measurement3.getFloatValue("YGC") > measurement2.getFloatValue("YGC"), "YGC didn't increase between measurements 1 and 2");
        assertThat(measurement3.getFloatValue("YGCT") > measurement2.getFloatValue("YGCT"), "YGCT time didn't increase between measurements 1 and 2");

    }

    private static void assertThat(boolean result, String message) {
        if (!result) {
            throw new RuntimeException(message);
        };
    }
}
