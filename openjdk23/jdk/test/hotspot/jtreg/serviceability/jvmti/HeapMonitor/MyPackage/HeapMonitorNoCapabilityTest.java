/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @summary Verifies the JVMTI Heap Monitor does not work without the required capability.
 * @requires vm.jvmti
 * @build Frame HeapMonitor
 * @compile HeapMonitorNoCapabilityTest.java
 * @run main/othervm/native -agentlib:HeapMonitorTest MyPackage.HeapMonitorNoCapabilityTest
 */

public class HeapMonitorNoCapabilityTest {
  private native static int allSamplingMethodsFail();

  public static void main(String[] args) {
    int result = allSamplingMethodsFail();

    if (result == 0) {
      throw new RuntimeException("Some methods could be called without a capability.");
    }
  }
}
