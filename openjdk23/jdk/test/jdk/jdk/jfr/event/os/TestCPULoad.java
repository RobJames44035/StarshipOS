/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.os;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.event.os.TestCPULoad
 */
public class TestCPULoad {
    private final static String EVENT_NAME = EventNames.CPULoad;

    public static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public static int burnCpuCycles(int limit) {
        int primeCount = 0;
        for (int i = 2; i < limit; i++) {
            if (isPrime(i)) {
                primeCount++;
            }
        }
        return primeCount;
    }

    public static void main(String[] args) throws Throwable {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME);
        recording.start();
        // burn some cycles to check increase of CPU related counters
        int pn = burnCpuCycles(2500000);
        recording.stop();
        System.out.println("Found " + pn + " primes while burning cycles");

        List<RecordedEvent> events = Events.fromRecording(recording);
        if (events.isEmpty()) {
            // CPU Load events are unreliable on Windows because
            // the way processes are identified with perf. counters.
            // See BUG 8010378.
            // Workaround is to detect Windows and allow
            // test to pass if events are missing.
            if (isWindows()) {
                return;
            }
            throw new AssertionError("Expected at least one event");
        }
        for (RecordedEvent event : events) {
            System.out.println("Event: " + event);
            for (String loadName : loadNames) {
                Events.assertField(event, loadName).atLeast(0.0f).atMost(1.0f);
            }
        }
    }

    private static final String[] loadNames = {"jvmUser", "jvmSystem", "machineTotal"};

    private static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }
}
