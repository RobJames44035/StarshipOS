/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test id=passive
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:+ShenandoahDegeneratedGC
 *      -Dtarget=10000
 *      TestLotsOfCycles
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:-ShenandoahDegeneratedGC
 *      -Dtarget=10000
 *      TestLotsOfCycles
 */

/*
 * @test id=aggressive
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -XX:+ShenandoahOOMDuringEvacALot
 *      -Dtarget=1000
 *      TestLotsOfCycles
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -XX:+ShenandoahAllocFailureALot
 *      -Dtarget=1000
 *      TestLotsOfCycles
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -Dtarget=1000
 *      TestLotsOfCycles
 */

/*
 * @test id=adaptive
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive
 *      -Dtarget=10000
 *      TestLotsOfCycles
 */

/*
 * @test id=generational
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      -Dtarget=10000
 *      TestLotsOfCycles
 */

/*
 * @test id=static
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=static
 *      -Dtarget=10000
 *      TestLotsOfCycles
 */

/*
 * @test id=compact
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm/timeout=480 -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact
 *      -Dtarget=1000
 *     TestLotsOfCycles
 */

public class TestLotsOfCycles {

    static final long TARGET_MB = Long.getLong("target", 10_000); // 10 Gb allocation, around 1K cycles to handle
    static final long STRIDE = 100_000;

    static volatile Object sink;

    public static void main(String[] args) throws Exception {
        long count = TARGET_MB * 1024 * 1024 / 16;
        for (long c = 0; c < count; c += STRIDE) {
            for (long s = 0; s < STRIDE; s++) {
                sink = new Object();
            }
            Thread.sleep(1);
        }
    }

}
