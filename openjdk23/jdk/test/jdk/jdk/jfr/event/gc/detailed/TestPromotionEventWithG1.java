/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.jfr.event.gc.detailed;

/**
 * @test
 * @bug 8212766
 * @key jfr
 * @summary Test that events are created when an object is aged or promoted during a GC and the copying of the object requires a new PLAB or direct heap allocation
 * @requires vm.hasJFR
 *
 * @requires (vm.gc == "G1" | vm.gc == null)
 *           & vm.opt.ExplicitGCInvokesConcurrent != true
 * @library /test/lib /test/jdk
 * @run main/othervm -Xmx32m -Xms32m -Xmn12m -XX:+UseG1GC -XX:-UseStringDeduplication -XX:MaxTenuringThreshold=5 -XX:InitialTenuringThreshold=5 jdk.jfr.event.gc.detailed.TestPromotionEventWithG1
 * @run main/othervm -Xmx32m -Xms32m -Xmn12m -XX:AllocatePrefetchLines=1 -XX:AllocateInstancePrefetchLines=1 -XX:AllocatePrefetchStepSize=16 -XX:AllocatePrefetchDistance=1 -XX:+UseG1GC
  *                  -XX:-UseStringDeduplication -Xlog:os+cpu=info -XX:MaxTenuringThreshold=5 -XX:InitialTenuringThreshold=5 -XX:MinTLABSize=768 -XX:TLABSize=768 jdk.jfr.event.gc.detailed.TestPromotionEventWithG1
 */
public class TestPromotionEventWithG1 {

    public static void main(String[] args) throws Throwable {
        PromotionEvent.test();
    }
}
