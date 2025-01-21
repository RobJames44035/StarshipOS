/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.api.consumer.log;

import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Period;
import jdk.jfr.Recording;
import jdk.jfr.StackTrace;

/**
 * @test
 * @summary Tests that large output is truncated
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @build jdk.jfr.api.consumer.log.LogAnalyzer
 * @run main/othervm
 *     -Xlog:jfr+event*=debug:file=truncate.log
 *     jdk.jfr.api.consumer.log.TestTruncation
 */
public class TestTruncation {

    @StackTrace(false) // Avoids '...' from stack trace truncation
    @Period("1 s")
    static class LargeEvent extends Event {
        String message1;
        String message2;
        String message3;
        String message4;
        String message5;
        String message6;
        String message7;
        String message8;
        String message9;
        String message10;
    }

    public static void main(String... args) throws Exception {
        FlightRecorder.addPeriodicEvent(LargeEvent.class, () -> {
            String text = "#".repeat(10_000);
            LargeEvent event = new LargeEvent();
            event.message1 = text;
            event.message2 = text;
            event.message3 = text;
            event.message4 = text;
            event.message5 = text;
            event.message6 = text;
            event.message7 = text;
            event.message8 = text;
            event.message9 = text;
            event.message10 = text;
            event.commit();
        });
        LogAnalyzer la = new LogAnalyzer("truncate.log");
        try (Recording r = new Recording()) {
            r.start();
            la.await("...");
        }
    }
}
