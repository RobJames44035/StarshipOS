/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package jdk.jfr.jvm;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;

/**
 * @test
 * @summary Verify that duplicate longer strings doesn't take up unneccessary space
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.jvm.TestLongStringsInPool
 */
public class TestLongStringsInPool {
    private static class StringEvent extends Event {
        String message;
    }

    public static void main(String[] args) throws Exception {
        // Create two recordings; first has only one large
        // string, second has several occurences of the same
        // string. With long strings (>128 chars) being pooled,
        // the two recording should be roughly the same size.
        final int numEvents = 10;
        final String longString = generateString();
        final int strLen = longString.length();
        final StringEvent event = new StringEvent();
        event.message = longString;

        Recording firstRec = new Recording();
        firstRec.start();
        // commit events with empty message (both recordings
        // will have the same number of events)
        for (int i = 0; i < numEvents - 1; i++) {
            event.message = "";
            event.commit();
        }
        // commit 1 event with a long string
        event.message = longString;
        event.commit();

        firstRec.stop();
        Path rec1 = Paths.get(".", "rec1.jfr");
        firstRec.dump(rec1);
        firstRec.close();


        Recording secondRec = new Recording();
        secondRec.start();
        // commit events with the same long string
        for (int i = 0; i < numEvents - 1; i++) {
          event.message = longString;
          event.commit();
        }
        // commit 1 event with a long string
        event.message = longString;
        event.commit();

        secondRec.stop();
        Path rec2 = Paths.get(".", "rec2.jfr");
        secondRec.dump(rec2);
        secondRec.close();

        // the files aren't exactly the same size, but rec2 should
        // not take up space for all strings if they're pooled correctly
        long maxAllowedDiff = (numEvents - 1) * strLen;
        long diff = Math.abs(Files.size(rec2) - Files.size(rec1));

        Asserts.assertTrue(diff <= maxAllowedDiff, "Size difference between recordings is too large: "+ diff +" > " + maxAllowedDiff);
        Asserts.assertFalse(RecordingFile.readAllEvents(rec1).isEmpty(), "No events found in recording 1");
        Asserts.assertFalse(RecordingFile.readAllEvents(rec2).isEmpty(), "No events found in recording 2");
        Asserts.assertEquals(RecordingFile.readAllEvents(rec1).size(), RecordingFile.readAllEvents(rec2).size(), "The recordings don't have the same number of events");
    }

    /**
     * Generate a string of 256 chars length.
     * @return
     */
    private static String generateString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            builder.append("abcdefgh");
        }
        return builder.toString();
    }
}
