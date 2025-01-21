/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.streaming;

import java.util.Random;

import jdk.jfr.Event;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Test that it is possible to iterate over chunk with normal events
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.streaming.TestFilledChunks
 */
public class TestFilledChunks {

    static class FillEvent extends Event {
        String message;
        int value;
        int id;
    }

    static class EndEvent extends Event {
    }

    // Will generate about 100 MB of data, or 8-9 chunks
    private static final int EVENT_COUNT = 5_000_000;

    public static void main(String... args) throws Exception {
        try (RecordingStream rs = new RecordingStream()) {
            rs.setOrdered(false);
            rs.onEvent(FillEvent.class.getName(), e -> {
                int id = e.getInt("id");
                // Some events may get lost due to
                // buffer being full.
                if (id > EVENT_COUNT / 2) {
                    rs.close();
                }
            });
            rs.startAsync();
            long seed = System.currentTimeMillis();
            System.out.println("Random seed: " + seed);
            Random r = new Random(seed);
            for (int i = 1; i < EVENT_COUNT; i++) {
                FillEvent f = new FillEvent();
                f.message = i % 2 == 0 ? "hello, hello, hello, hello, hello!" : "hi!";
                f.value = r.nextInt(10000);
                f.id = i;
                f.commit();
                if (i % 1_000_000 == 0) {
                    System.out.println("Emitted " + i + " events");
                }
            }
            rs.awaitTermination();
        }
    }
}
