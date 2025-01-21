/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test id=passive
 * @summary Acceptance tests: collector can deal with retained objects
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:+ShenandoahDegeneratedGC -XX:+ShenandoahVerify
 *      TestRetainObjects
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:-ShenandoahDegeneratedGC -XX:+ShenandoahVerify
 *      TestRetainObjects
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:+ShenandoahDegeneratedGC
 *      TestRetainObjects
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:-ShenandoahDegeneratedGC
 *      TestRetainObjects
 */

/*
 * @test id=aggressive
 * @summary Acceptance tests: collector can deal with retained objects
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -XX:+ShenandoahOOMDuringEvacALot
 *      TestRetainObjects
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -XX:+ShenandoahAllocFailureALot
 *      TestRetainObjects
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      TestRetainObjects
 */

/*
 * @test id=adaptive
 * @summary Acceptance tests: collector can deal with retained objects
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive
 *      -XX:+ShenandoahVerify
 *      TestRetainObjects
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive
 *      TestRetainObjects
 */

/*
 * @test id=generational
 * @summary Acceptance tests: collector can deal with retained objects
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      -XX:+ShenandoahVerify
 *      TestRetainObjects
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      -XX:+ShenandoahOOMDuringEvacALot -XX:+ShenandoahVerify
 *      TestRetainObjects
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      -XX:+ShenandoahAllocFailureALot -XX:+ShenandoahVerify
 *      TestRetainObjects
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive -XX:ShenandoahGCMode=generational
 *      TestRetainObjects
 */

/*
 * @test id=static
 * @summary Acceptance tests: collector can deal with retained objects
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=static
 *      TestRetainObjects
 */

/*
 * @test id=compact
 * @summary Acceptance tests: collector can deal with retained objects
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact
 *      TestRetainObjects
 */

/*
 * @test id=no-tlab
 * @summary Acceptance tests: collector can deal with retained objects
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx1g -Xms1g -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC
 *      -XX:-UseTLAB -XX:+ShenandoahVerify
 *      TestRetainObjects
 */

public class TestRetainObjects {

    static final int COUNT = 10_000_000;
    static final int WINDOW = 10_000;

    static final String[] reachable = new String[WINDOW];

    public static void main(String[] args) throws Exception {
        int rIdx = 0;
        for (int c = 0; c < COUNT; c++) {
            reachable[rIdx] = ("LargeString" + c);
            rIdx++;
            if (rIdx >= WINDOW) {
                rIdx = 0;
            }
        }
    }

}
