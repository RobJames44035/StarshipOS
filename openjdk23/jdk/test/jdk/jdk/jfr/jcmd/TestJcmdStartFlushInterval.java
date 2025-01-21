/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.jcmd;

import java.lang.reflect.Method;
import java.time.Duration;

import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;

/**
 * @test
 * @summary Start a recording with a flush interval
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules jdk.jfr/jdk.jfr:open
 * @run main/othervm jdk.jfr.jcmd.TestJcmdStartFlushInterval
 */
public class TestJcmdStartFlushInterval {

    public static void main(String[] args) throws Exception {
        JcmdHelper.jcmd("JFR.start", "flush-interval=2s");
        Method getFlushIntervalMethod = Recording.class.getDeclaredMethod("getFlushInterval");
        getFlushIntervalMethod.setAccessible(true);
        for (Recording r : FlightRecorder.getFlightRecorder().getRecordings()) {
            Duration d = (Duration)getFlushIntervalMethod.invoke(r);
            if (d.equals(Duration.ofSeconds(2))) {
                return; //OK
            } else {
                throw new Exception("Unexpected flush-interval=" + d);
            }
        }
        throw new Exception("No recording found");
    }

}
