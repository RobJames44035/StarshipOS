/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.consumer;

import java.nio.file.Path;
import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedThread;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.Utils;

/**
 * @test
 * @summary Tests that the RecordedEvent.getThread() returns th expected info
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestRecordedEventGetThreadOther
 */
public class TestRecordedEventGetThreadOther {

    private static final String MY_THREAD_NAME = "MY_THREAD_NAME";

    static class TestEvent extends Event {
    }

    static class PostingThread extends Thread {
        private final Path dumpFilePath;
        PostingThread(Path dumpFilePath) {
            this.dumpFilePath = dumpFilePath;
        }

        @Override
        public void run() {
            try {
                System.out.println("Starting thread...");
                try (Recording r = new Recording()) {
                    r.start();
                    TestEvent t = new TestEvent();
                    t.commit();
                    r.stop();
                    r.dump(dumpFilePath);
                    System.out.println("events dumped to the file " + dumpFilePath);
                }
            } catch (Throwable t) {
                t.printStackTrace();
                Asserts.fail();
            }
        }
    }

    public static void main(String[] args) throws Exception  {
        Path dumpFilePath = Utils.createTempFile("event-thread", ".jfr");

        PostingThread thread = new PostingThread(dumpFilePath);
        thread.setName(MY_THREAD_NAME);
        thread.start();
        thread.join();

        List<RecordedEvent> events = RecordingFile.readAllEvents(dumpFilePath);
        Asserts.assertEquals(events.size(), 1);

        RecordedEvent event = events.getFirst();
        RecordedThread recordedThread = event.getThread();

        Asserts.assertNotNull(recordedThread);
        Asserts.assertEquals(recordedThread.getJavaName(), MY_THREAD_NAME);
        Asserts.assertEquals(recordedThread.getJavaThreadId(), thread.getId());
        Asserts.assertNotNull(recordedThread.getId());
        Asserts.assertEquals(recordedThread.getOSName(), MY_THREAD_NAME);
    }
}
