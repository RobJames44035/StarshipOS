/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.jfr.consumer.RecordedEvent;
import jdk.management.jfr.FlightRecorderMXBean;
import jdk.test.lib.jfr.SimpleEventHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.TestStream
 */
public class TestStream {
    public static void main(String[] args) throws Exception {
        FlightRecorderMXBean bean = JmxHelper.getFlighteRecorderMXBean();

        Instant startTime = Instant.now();
        SimpleEventHelper.createEvent(0);

        long recId = bean.newRecording();
        bean.startRecording(recId);
        SimpleEventHelper.createEvent(1);

        bean.stopRecording(recId);
        SimpleEventHelper.createEvent(2);

        Instant endTime = Instant.now();
        // Test with ISO-8601
        Map<String, String> options = new HashMap<>();
        options.put("startTime", startTime.toString());
        options.put("endTime", endTime.toString());
        options.put("blockSize", String.valueOf(50_000));
        verifyStream(bean, recId, options);
        // Test with milliseconds since epoch
        options.put("startTime", Long.toString(startTime.toEpochMilli()));
        options.put("endTime", Long.toString(endTime.toEpochMilli()));
        options.put("blockSize", String.valueOf(150_000));
        verifyStream(bean, recId, options);

        bean.closeRecording(recId);
    }

    private static void verifyStream(FlightRecorderMXBean bean, long recId, Map<String, String> options) throws IOException, Exception {
        long streamId = bean.openStream(recId, options);

        List<RecordedEvent> events = JmxHelper.parseStream(streamId, bean);
        SimpleEventHelper.verifyContains(events, 1);
        SimpleEventHelper.verifyNotContains(events, 0, 2);
        bean.closeStream(streamId);
    }
}
