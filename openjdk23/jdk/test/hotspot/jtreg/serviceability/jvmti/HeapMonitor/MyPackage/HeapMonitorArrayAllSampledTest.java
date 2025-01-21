/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @build Frame HeapMonitor
 * @summary Verifies the JVMTI Heap Monitor interval when allocating arrays.
 * @requires vm.jvmti
 * @compile HeapMonitorArrayAllSampledTest.java
 * @run main/othervm/native -agentlib:HeapMonitorTest MyPackage.HeapMonitorArrayAllSampledTest
 */

public class HeapMonitorArrayAllSampledTest {

  // Do 1000 iterations and expect maxIteration samples.
  private static final int maxIteration = 1000;
  private static int array[];

  private static void allocate(int size) {
    for (int j = 0; j < maxIteration; j++) {
      array = new int[size];
    }
  }

  public static void main(String[] args) {
    int sizes[] = {1000, 10000, 100000, 1000000};

    HeapMonitor.sampleEverything();

    for (int currentSize : sizes) {
      System.out.println("Testing size " + currentSize);

      HeapMonitor.resetEventStorage();
      allocate(currentSize);

      // 10% error ensures a sanity test without becoming flaky.
      // Flakiness is due to the fact that this test is dependent on the sampling interval, which is a
      // statistical geometric variable around the sampling interval. This means that the test could be
      // unlucky and not achieve the mean average fast enough for the test case.
      if (!HeapMonitor.statsHaveExpectedNumberSamples(maxIteration, 10)) {
        throw new RuntimeException("Statistics should show about " + maxIteration + " samples.");
      }
    }
  }
}
