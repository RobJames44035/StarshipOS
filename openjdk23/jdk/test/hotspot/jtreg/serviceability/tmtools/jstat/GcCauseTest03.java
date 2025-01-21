/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test checks output displayed with jstat -gccause.
 *          Test scenario:
 *          test forces debuggee application call System.gc(), runs jstat and checks that
 *          cause of last garbage collection displayed by jstat (LGCC) is 'System.gc()'.
 * @requires vm.gc != "Z" & vm.gc != "Shenandoah"
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library ../share
 * @run main/othervm -XX:+UsePerfData -Xmx128M -XX:MaxMetaspaceSize=128M GcCauseTest03
 */
import utils.*;

public class GcCauseTest03 {

    private final static float targetMemoryUsagePercent = 0.7f;

    public static void main(String[] args) throws Exception {

        // We will be running "jstat -gc" tool
        JstatGcCauseTool jstatGcTool = new JstatGcCauseTool(ProcessHandle.current().pid());

        System.gc();

        // Run once and get the  results asserting that they are reasonable
        JstatGcCauseResults measurement = jstatGcTool.measure();
        measurement.assertConsistency();

        if (measurement.valueExists("LGCC")) {
            if (!"System.gc()".equals(measurement.getStringValue("LGCC"))) {
                throw new RuntimeException("Unexpected GC cause: " + measurement.getStringValue("LGCC") + ", expected System.gc()");
            }
        }

    }
}
