/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.api.consumer.log;

import jdk.jfr.Recording;

/**
 * @test
 * @summary Tests that event logging can't be turned on and off
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @build jdk.jfr.api.consumer.log.LogAnalyzer
 * @run main/othervm
 *      -Xlog:jfr+event*=debug,jfr+system=debug:file=disk-on-off.log
 *      jdk.jfr.api.consumer.log.TestDiskOnOff
 */
public class TestDiskOnOff {

    public static void main(String... args) throws Exception {
        LogAnalyzer la = new LogAnalyzer("disk-on-off.log");
        try (Recording r = new Recording()) {
            r.start();
            la.await("Log stream started");
        }
        la.await("Log stream stopped");
        try (Recording r = new Recording()) {
            r.start();
            la.await("Log stream started");
        }
        la.await("Log stream stopped");
    }
}
