/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test id=rotation
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+ShenandoahRegionSampling
 *      -Xlog:gc+region=trace:region-snapshots-%p.log::filesize=100,filecount=3
 *      -XX:+UseShenandoahGC
 *      TestShenandoahRegionLogging
 */
import java.io.File;

public class TestShenandoahRegionLogging {
    public static void main(String[] args) throws Exception {
        System.gc();

        File directory = new File(".");
        File[] files = directory.listFiles((dir, name) -> name.startsWith("region-snapshots"));

        // Expect one or more log files when region logging is enabled
        if (files.length == 0) {
            throw new Error("Expected at least one log file for region sampling data.");
        }
    }
}
