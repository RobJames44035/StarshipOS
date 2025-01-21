/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/* @test
 * @summary test single worker threaded Shenandoah
 * @requires vm.gc.Shenandoah
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions
 *                   -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=aggressive
 *                   -XX:ParallelGCThreads=1 -XX:ConcGCThreads=1 TestSingleThreaded
 */

public class TestSingleThreaded {

    public static void main(String[] args) {
        // Bug should crash before we get here.
    }
}
