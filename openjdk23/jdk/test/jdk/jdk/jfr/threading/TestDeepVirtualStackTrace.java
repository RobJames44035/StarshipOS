/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package jdk.jfr.threading;

import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.Name;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedFrame;
import jdk.jfr.consumer.RecordedMethod;
import jdk.jfr.consumer.RecordedStackTrace;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Tests emitting an event, both in Java and native, in a virtual
 *          thread with the maximum number of allowed stack frames for JFR
 * @key jfr
 * @requires vm.hasJFR & vm.continuations
 * @library /test/lib /test/jdk
 * @modules jdk.jfr/jdk.jfr.internal
 * @run main/othervm -XX:MaxNewSize=40M -XX:FlightRecorderOptions:stackdepth=2048
 *      jdk.jfr.threading.TestDeepVirtualStackTrace
 */
public class TestDeepVirtualStackTrace {

    public final static int FRAME_COUNT = 2048;

    @Name("test.Deep")
    private static class TestEvent extends Event {
    }

    public static Object[] allocated;

    public static void main(String... args) throws Exception {
        testJavaEvent();
        testNativeEvent();
    }

    private static void testJavaEvent() throws Exception {
        assertStackTrace(() -> deepevent(FRAME_COUNT), "test.Deep", "deepevent");
    }

    private static void deepevent(int depth) {
        if (depth == 0) {
            TestEvent e = new TestEvent();
            e.commit();
            System.out.println("Emitted Deep event");
            return;
        }
        deepevent(depth - 1);
    }

    private static void testNativeEvent() throws Exception {
        assertStackTrace(() -> deepsleep(FRAME_COUNT), "jdk.ObjectAllocationOutsideTLAB", "sleep");
    }

    private static void deepsleep(int depth) {
        if (depth == 0) {
            // The TLAB max size is not limited explicitly
            // So the test limit max size of young generation to allocate outside TLAB
            allocated = new Object[10_000_000];
            System.out.println("Emitted ObjectAllocationOutsideTLAB event");
            return;
        }
        deepsleep(depth - 1);
    }

    private static void assertStackTrace(Runnable eventEmitter, String eventName, String stackMethod) throws Exception {
        System.out.println();
        System.out.println("Testing event: " + eventName);
        System.out.println("=============================");

        boolean isTargetEventFound = false;

        try (Recording r = new Recording()) {
            r.enable(eventName).withoutThreshold();
            r.start();
            Thread vt = Thread.ofVirtual().start(eventEmitter);
            vt.join();
            r.stop();
            List<RecordedEvent> events = Events.fromRecording(r);
            Asserts.assertFalse(events.isEmpty(), "No event found in virtual thread");
            for (RecordedEvent event : events) {
                System.out.println(event);
                RecordedStackTrace stackTrace = event.getStackTrace();
                if (stackTrace == null) {
                    continue;
                }
                List<RecordedFrame> frames = stackTrace.getFrames();
                int count = 0;
                boolean isTargetEvent = false;
                boolean isFirstFrame = true;
                for (int c = 0; c < frames.size(); c++) {
                    RecordedFrame frame = frames.get(c);
                    Asserts.assertTrue(frame.isJavaFrame());
                    Asserts.assertNotNull(frame.getMethod());
                    RecordedMethod m = frame.getMethod();
                    Asserts.assertNotNull(m.getType());
                    if (m.getName().contains(stackMethod)) {
                        if (c == 0) {
                            isTargetEvent = true;
                        }
                        count++;
                    }
                }
                if (isTargetEvent) {
                    isTargetEventFound = true;
                    Asserts.assertTrue(stackTrace.isTruncated());
                    Asserts.assertEquals(count, FRAME_COUNT);
                    Asserts.assertEquals(frames.size(), FRAME_COUNT);
                }
            }
        }

        Asserts.assertTrue(isTargetEventFound, "At least one target event found");
    }

}
