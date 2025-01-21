/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @summary Verifies the JVMTI Heap Monitor API
 * @requires vm.jvmti
 * @build Frame HeapMonitor
 * @compile HeapMonitorTest.java
 * @run main/othervm/native -agentlib:HeapMonitorTest MyPackage.HeapMonitorTest
 */

public class HeapMonitorTest {

  private static native boolean framesAreNotLive(Frame[] frames);

  public static void main(String[] args) {
    if (args.length > 0) {
      // For interpreter mode, have a means to reduce the default iteration count.
      HeapMonitor.setAllocationIterations(Integer.parseInt(args[0]));
    }

    HeapMonitor.allocateAndCheckFrames();

    HeapMonitor.disableSamplingEvents();
    HeapMonitor.resetEventStorage();
    if (!HeapMonitor.eventStorageIsEmpty()) {
      throw new RuntimeException("Storage is not empty after reset.");
    }

    HeapMonitor.allocate();
    if (!HeapMonitor.eventStorageIsEmpty()) {
      throw new RuntimeException("Storage is not empty after allocation while disabled.");
    }
  }
}
