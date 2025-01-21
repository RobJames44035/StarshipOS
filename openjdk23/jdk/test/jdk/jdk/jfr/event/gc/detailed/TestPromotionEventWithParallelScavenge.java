/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package jdk.jfr.event.gc.detailed;

/**
 * @test
 * @key jfr
 * @summary Test that events are created when an object is aged or promoted during a GC and the copying of the object requires a new PLAB or direct heap allocation
 * @requires vm.hasJFR
 *
 * @requires vm.gc == "Parallel" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -Xmx32m -Xms32m -Xmn12m -XX:+UseParallelGC -XX:MaxTenuringThreshold=5 -XX:InitialTenuringThreshold=5 jdk.jfr.event.gc.detailed.TestPromotionEventWithParallelScavenge
 */
public class TestPromotionEventWithParallelScavenge {

    public static void main(String[] args) throws Throwable {
        PromotionEvent.test();
    }
}
