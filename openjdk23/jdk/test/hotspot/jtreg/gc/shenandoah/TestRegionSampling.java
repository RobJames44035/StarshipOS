/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test id=passive
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -XX:+ShenandoahRegionSampling
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:+ShenandoahDegeneratedGC
 *      TestRegionSampling
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -XX:+ShenandoahRegionSampling
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:-ShenandoahDegeneratedGC
 *      TestRegionSampling
 */

/*
 * @test id=adaptive
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -XX:+ShenandoahRegionSampling
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive
 *      TestRegionSampling
 */

/*
 * @test id=generational
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -XX:+ShenandoahRegionSampling
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      TestRegionSampling
 */

/*
 * @test id=static
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -XX:+ShenandoahRegionSampling
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=static
 *      TestRegionSampling
 */

/*
 * @test id=compact
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -XX:+ShenandoahRegionSampling
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact
 *      TestRegionSampling
 */

/*
 * @test id=aggressive
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -XX:+ShenandoahRegionSampling
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      TestRegionSampling
 */

public class TestRegionSampling {

    static final long TARGET_MB = Long.getLong("target", 2_000); // 2 Gb allocation

    static volatile Object sink;

    public static void main(String[] args) throws Exception {
        long count = TARGET_MB * 1024 * 1024 / 16;
        for (long c = 0; c < count; c++) {
            sink = new Object();
        }
    }

}
