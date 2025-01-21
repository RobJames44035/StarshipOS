/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package jdk.jfr.event.profiling;

import java.time.Duration;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingStream;
import jdk.jfr.internal.JVM;
import jdk.test.lib.jfr.EventNames;

/*
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @modules jdk.jfr/jdk.jfr.internal
 * @run main jdk.jfr.event.profiling.TestNative
 */
public class TestNative {

    final static String NATIVE_EVENT = EventNames.NativeMethodSample;

    static volatile boolean alive = true;

    public static void main(String[] args) throws Exception {
        try (RecordingStream rs = new RecordingStream()) {
            rs.enable(NATIVE_EVENT).withPeriod(Duration.ofMillis(1));
            rs.onEvent(NATIVE_EVENT, e -> {
                alive = false;
                rs.close();
            });
            Thread t = new Thread(TestNative::nativeMethod);
            t.setDaemon(true);
            t.start();
            rs.start();
        }

    }

    public static void nativeMethod() {
        while (alive) {
            JVM.getPid();
        }
    }
}
