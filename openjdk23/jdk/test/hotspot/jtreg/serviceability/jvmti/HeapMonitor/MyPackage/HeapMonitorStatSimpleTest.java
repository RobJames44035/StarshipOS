/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @build Frame HeapMonitor
 * @requires vm.jvmti
 * @summary Verifies the JVMTI Heap Monitor events are only sent after enabling.
 * @compile HeapMonitorStatSimpleTest.java
 * @run main/othervm/native -agentlib:HeapMonitorTest MyPackage.HeapMonitorStatSimpleTest
 */

public class HeapMonitorStatSimpleTest {
  private native static int areSamplingStatisticsZero();

  public static void main(String[] args) {
    HeapMonitor.allocate();
    if (!HeapMonitor.eventStorageIsEmpty()) {
      throw new RuntimeException("Statistics should be null to begin with.");
    }

    HeapMonitor.enableSamplingEvents();
    HeapMonitor.allocate();

    if (HeapMonitor.eventStorageIsEmpty()) {
      throw new RuntimeException("Statistics should not be null now.");
    }
  }
}
