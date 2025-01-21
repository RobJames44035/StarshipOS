/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.heapsummary;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "Serial" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UseSerialGC jdk.jfr.event.gc.heapsummary.TestHeapSummaryEventDefNewSerial
 */

/**
 * @test
 * @bug 8264008
 * @key jfr
 * @requires vm.hasJFR & vm.bits == 64
 * @requires vm.gc == "Serial" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UseSerialGC -XX:-UseCompressedClassPointers
 *                   jdk.jfr.event.gc.heapsummary.TestHeapSummaryEventDefNewSerial
 */
public class TestHeapSummaryEventDefNewSerial {
    public static void main(String[] args) throws Exception {
        HeapSummaryEventAllGcs.test(GCHelper.gcDefNew, GCHelper.gcSerialOld);
    }
}
