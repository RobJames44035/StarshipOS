/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

import java.util.List;

/**
 * @test
 * @summary Verifies if turning off the event notification stops events.
 * @requires vm.jvmti
 * @build Frame HeapMonitor
 * @compile HeapMonitorEventOnOffTest.java
 * @run main/othervm/native -agentlib:HeapMonitorTest MyPackage.HeapMonitorEventOnOffTest
 */
public class HeapMonitorEventOnOffTest {
  public static void main(String[] args) {
    HeapMonitor.allocateAndCheckFrames();

    // Disabling the notification system should stop events.
    HeapMonitor.disableSamplingEvents();
    HeapMonitor.resetEventStorage();
    HeapMonitor.allocateAndCheckFrames(false, false);

    // By calling allocateAndCheckFrames(), we enable the notifications and check if allocations
    // get sampled again.
    HeapMonitor.allocateAndCheckFrames();
  }
}
