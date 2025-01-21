/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test id=passive
 * @summary Test Shenandoah GC uses concurrent/parallel threads correctly
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCMode=passive
 *      -XX:ConcGCThreads=2 -XX:ParallelGCThreads=4
 *      -Dtarget=1000
 *      TestGCThreadGroups
 */

/**
 * @test id=default
 * @summary Test Shenandoah GC uses concurrent/parallel threads correctly
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC
 *      -XX:ConcGCThreads=2 -XX:ParallelGCThreads=4
 *      -Dtarget=1000
 *      TestGCThreadGroups
 *
 * @run main/othervm -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC
 *      -XX:-UseDynamicNumberOfGCThreads
 *      -Dtarget=1000
 *      TestGCThreadGroups
 *
 * @run main/othervm -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=adaptive
 *      -XX:ConcGCThreads=2 -XX:ParallelGCThreads=4
 *      -Dtarget=1000
 *      TestGCThreadGroups
 *
 * @run main/othervm -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=static
 *      -XX:ConcGCThreads=2 -XX:ParallelGCThreads=4
 *      -Dtarget=1000
 *      TestGCThreadGroups
 *
 * @run main/othervm -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact
 *      -XX:ConcGCThreads=2 -XX:ParallelGCThreads=4
 *      -Dtarget=100
 *      TestGCThreadGroups
 *
 * @run main/othervm -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *      -XX:ConcGCThreads=2 -XX:ParallelGCThreads=4
 *      -Dtarget=100
 *      TestGCThreadGroups
 */

/**
 * @test id=generational
 * @summary Test Shenandoah GC uses concurrent/parallel threads correctly
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC
 *      -XX:ConcGCThreads=2 -XX:ParallelGCThreads=4
 *      -Dtarget=1000 -XX:ShenandoahGCMode=generational
 *      TestGCThreadGroups
 *
 * @run main/othervm -Xmx16m -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *      -XX:+UseShenandoahGC
 *      -XX:-UseDynamicNumberOfGCThreads
 *      -Dtarget=1000 -XX:ShenandoahGCMode=generational
 *      TestGCThreadGroups
 */

public class TestGCThreadGroups {

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
