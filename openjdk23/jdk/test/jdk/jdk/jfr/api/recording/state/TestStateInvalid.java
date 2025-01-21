/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.state;

import jdk.jfr.Recording;
import jdk.jfr.RecordingState;
import jdk.test.lib.jfr.CommonHelper;
import jdk.test.lib.jfr.VoidFunction;

/**
 * @test
 * @summary Test start/stop/close recording from different recording states.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.state.TestStateInvalid
 */
public class TestStateInvalid {

    public static void main(String[] args) throws Throwable {
        Recording r = new Recording();
        CommonHelper.verifyRecordingState(r, RecordingState.NEW);
        verifyIllegalState(() -> r.stop(), "stop() when not started");
        CommonHelper.verifyRecordingState(r, RecordingState.NEW);

        r.start();
        CommonHelper.verifyRecordingState(r, RecordingState.RUNNING);
        verifyIllegalState(() -> r.start(), "double start()");
        CommonHelper.verifyRecordingState(r, RecordingState.RUNNING);

        r.stop();
        CommonHelper.verifyRecordingState(r, RecordingState.STOPPED);
        verifyIllegalState(() -> r.stop(), "double stop()");
        verifyIllegalState(() -> r.start(), "start() after stop()");
        CommonHelper.verifyRecordingState(r, RecordingState.STOPPED);

        r.close();
        CommonHelper.verifyRecordingState(r, RecordingState.CLOSED);
        verifyIllegalState(() -> r.stop(), "stop() after close()");
        verifyIllegalState(() -> r.start(), "start() after close()");
        CommonHelper.verifyRecordingState(r, RecordingState.CLOSED);
    }

    private static void verifyIllegalState(VoidFunction f, String msg) throws Throwable {
        CommonHelper.verifyException(f, msg, IllegalStateException.class);
    }
}
