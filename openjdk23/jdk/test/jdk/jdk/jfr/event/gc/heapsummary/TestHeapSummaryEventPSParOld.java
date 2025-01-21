/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.heapsummary;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "Parallel" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -XX:+UseParallelGC jdk.jfr.event.gc.heapsummary.TestHeapSummaryEventPSParOld
 */
public class TestHeapSummaryEventPSParOld {
    public static void main(String[] args) throws Exception {
        HeapSummaryEventAllGcs.test(GCHelper.gcParallelScavenge, GCHelper.gcParallelOld);
    }
}
