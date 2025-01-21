/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.consumer;

import java.io.EOFException;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.SimpleEvent;

/**
 * @test
 * @summary Verifies that RecordingFile.readEvent() throws EOF when past the last record
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestRecordingFileReadEventEof
 */
public class TestRecordingFileReadEventEof {

    public static void main(String[] args) throws Throwable {
        try (Recording r = new Recording()) {
            r.start();
            SimpleEvent t = new SimpleEvent();
            t.commit();
            r.stop();
            RecordingFile file = Events.copyTo(r);
            file.readEvent();
            try {
                file.readEvent();
                Asserts.fail("Expected EOFException not thrown");
            } catch (EOFException x) {
                // OK, as expected
            }
        }
    }
}
