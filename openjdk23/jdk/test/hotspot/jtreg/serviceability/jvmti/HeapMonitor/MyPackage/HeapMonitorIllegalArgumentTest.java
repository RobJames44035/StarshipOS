/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @summary Verifies the JVMTI SetHeapSamplingInterval returns an illegal argument for negative ints.
 * @requires vm.jvmti
 * @build Frame HeapMonitor
 * @compile HeapMonitorIllegalArgumentTest.java
 * @run main/othervm/native -agentlib:HeapMonitorTest MyPackage.HeapMonitorIllegalArgumentTest
 */

public class HeapMonitorIllegalArgumentTest {
  private native static int testIllegalArgument();

  public static void main(String[] args) {
    int result = testIllegalArgument();

    if (result == 0) {
      throw new RuntimeException("Test illegal argument failed.");
    }
  }
}
