/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.jfr.api.consumer.recordingstream;

import jdk.jfr.consumer.RecordingStream;
import jdk.test.lib.jfr.EventNames;

/**
* @test
* @bug 8257424
* @summary Tests recording name for RecordingStrream
* @key jfr
* @requires vm.hasJFR
* @library /test/lib
* @run main/othervm jdk.jfr.api.consumer.recordingstream.TestRecordingName
*/
public class TestRecordingName {

   public static void main(String... args) throws Exception {
       try (RecordingStream r = new RecordingStream()) {
           r.enable(EventNames.ActiveRecording);
           r.onEvent(e -> {
               System.out.println(e);
               String name = e.getString("name");
               if (name.startsWith("Recording Stream: ")) {
                   r.close();
                   return;
               }
               System.err.println("Recording name not set, was " + name + ", but expected \"Recording Stream: <Instant>\"");
           });
           r.start();
       }
   }
}
