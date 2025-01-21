/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package gc.z;

/**
 * @test TestZForceDiscontiguousHeapReservations
 * @requires vm.gc.Z & vm.debug
 * @summary Test the ZForceDiscontiguousHeapReservations development flag
 * @library /test/lib
 * @run driver gc.z.TestZForceDiscontiguousHeapReservations
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestZForceDiscontiguousHeapReservations {

    private static void testValue(int n) throws Exception  {
        /**
         *  Xmx is picked so that it is divisible by 'ZForceDiscontiguousHeapReservations * ZGranuleSize'
         *  Xms is picked so that it is less than '16 * Xmx / ZForceDiscontiguousHeapReservations' as ZGC
         *   cannot currently handle a discontiguous heap with an initial size larger than the individual
         *   reservations.
         */
        final int XmxInM = 2000;
        final int XmsInM = Math.min(16 * XmxInM / (n + 1), XmxInM);
        OutputAnalyzer oa = ProcessTools.executeTestJava(
            "-XX:+UseZGC",
            "-Xms" + XmsInM + "M",
            "-Xmx" + XmxInM + "M",
            "-Xlog:gc,gc+init",
            "-XX:ZForceDiscontiguousHeapReservations=" + n,
            "-version")
                .outputTo(System.out)
                .errorTo(System.out)
                .shouldHaveExitValue(0);
        if (n > 1) {
            oa.shouldContain("Address Space Type: Discontiguous");
        }
    }

    public static void main(String[] args) throws Exception {
        testValue(0);
        testValue(1);
        testValue(2);
        testValue(100);
    }
}
