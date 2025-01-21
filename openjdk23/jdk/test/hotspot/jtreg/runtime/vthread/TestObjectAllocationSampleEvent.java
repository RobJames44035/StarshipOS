/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
import jdk.jfr.consumer.RecordingStream;

/**
 * @test Re-written from jfr test ObjectAllocationSampleEvent
 * @summary Tests ObjectAllocationSampleEvent
 * @requires vm.hasJFR
 * @library /test/lib
 * @compile TestObjectAllocationSampleEvent.java
 * @run main/othervm TestObjectAllocationSampleEvent
 */
public class TestObjectAllocationSampleEvent {

    public static void main(String... args) throws Exception {
        Thread thread = Thread.ofVirtual().start(new Task());
        thread.join();
    }
}

class Task implements Runnable {

    public void run() {

        // Needs to wait for crash...
        try {
            RecordingStream rs = new RecordingStream();
            Thread.sleep(1_000);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
