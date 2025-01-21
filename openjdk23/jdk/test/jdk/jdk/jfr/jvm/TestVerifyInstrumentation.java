/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.jfr.jvm;

import jdk.jfr.Recording;
import jdk.test.lib.jfr.EventNames;

/**
 * @test
 * @bug 8316271
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm -Xverify:all jdk.jfr.jvm.TestVerifyInstrumentation
 */
public class TestVerifyInstrumentation {
    private final static String EVENT_NAME = EventNames.ThreadSleep;

    public static void main(String[] args) {
        // This thread sleep wil load the jdk.internal.event.ThreadSleepEvent
        // before JFR has started recording. This will set the type of the static
        // field EventConfiguration to become untyped, i.e. java.lang.Object.
        // Before issuing an invokevirtual instructions for this receiver, we must perform
        // the proper type conversion, i.e. a downcast to type EventConfiguration using
        // a conditional checkcast. -Xverify:all asserts this is ok.
        //
        // If not ok, the following exception is thrown as part of retransformation:
        //
        // java.lang.RuntimeException: JfrJvmtiAgent::retransformClasses failed: JVMTI_ERROR_FAILS_VERIFICATION.
        //
        try {
            Thread.sleep(1);
        } catch (InterruptedException ie) {}
        try (Recording recording = new Recording()) {
            recording.enable(EVENT_NAME).withoutThreshold().withStackTrace();
            recording.start();
            recording.stop();
        }
    }
}
