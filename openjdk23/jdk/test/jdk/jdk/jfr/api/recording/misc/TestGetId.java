/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.misc;

import java.util.HashMap;
import java.util.Map;

import jdk.jfr.Recording;
import jdk.test.lib.Asserts;

/**
 * @test
 * @summary Verify that each recording get unique a id
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm  jdk.jfr.api.recording.misc.TestGetId
 */
public class TestGetId {

    public static void main(String[] args) throws Throwable {
        Map<Long, Recording> recordings = new HashMap<>();

        final int iterations = 100;
        for (int i = 0; i < iterations; ++i) {
            Recording newRecording = new Recording();
            System.out.println("created: " + newRecording.getId());
            Recording oldRecording = recordings.get(newRecording.getId());
            Asserts.assertNull(oldRecording, "Duplicate recording ID: " + newRecording.getId());
            recordings.put(newRecording.getId(), newRecording);

            if (i % 3 == 0) {
                Recording closeRecording = recordings.remove(recordings.keySet().iterator().next());
                Asserts.assertNotNull(closeRecording, "Could not find recording in map. Test error.");
                closeRecording.close();
                System.out.println("closed: " + closeRecording.getId());
            }
        }

        for (Recording r : recordings.values()) {
            r.close();
        }
    }

}
