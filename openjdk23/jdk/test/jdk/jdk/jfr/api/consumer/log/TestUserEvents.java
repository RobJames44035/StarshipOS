/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.api.consumer.log;

import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Name;
import jdk.jfr.Period;

/**
 * @test
 * @summary Tests that only user events are emitted
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @build jdk.jfr.api.consumer.log.LogAnalyzer
 * @run main/othervm
 *      -Xlog:jfr+event=trace:file=user.log
 *      -XX:StartFlightRecording
 *      jdk.jfr.api.consumer.log.TestUserEvents
 */
public class TestUserEvents {
    // Testing with -XX:StartFlightRecording, since it's
    // a likely use case and there could be issues
    // with starting the stream before main.
    @Period("1 s")
    @Name("UserDefined")
    static class UserEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        FlightRecorder.addPeriodicEvent(UserEvent.class, () -> {
            UserEvent e = new UserEvent();
            e.commit();
        });
        LogAnalyzer la = new LogAnalyzer("user.log");
        la.await("UserDefined");
        la.shouldNotContain("CPULoad"); // Emitted 1/s
    }
}
