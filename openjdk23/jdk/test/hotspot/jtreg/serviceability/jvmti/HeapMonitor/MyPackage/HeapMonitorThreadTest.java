/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @build Frame HeapMonitor ThreadInformation
 * @summary Verifies the JVMTI Heap Monitor Thread information sanity.
 * @requires vm.jvmti
 * @compile HeapMonitorThreadTest.java
 * @run main/othervm/native -Xmx512m -agentlib:HeapMonitorTest MyPackage.HeapMonitorThreadTest
 */

import java.util.List;

public class HeapMonitorThreadTest {
  private native static boolean checkSamples(int numThreads);

  public static void main(String[] args) {
    final int numThreads = 5;
    List<ThreadInformation> threadList = ThreadInformation.createThreadList(numThreads);

    // Sample at a interval of 8k.
    HeapMonitor.setSamplingInterval(1 << 13);
    HeapMonitor.enableSamplingEvents();

    System.err.println("Starting threads");
    ThreadInformation.startThreads(threadList);
    ThreadInformation.waitForThreads(threadList);
    System.err.println("Waited for threads");

    if (!checkSamples(numThreads)) {
      throw new RuntimeException("Problem with checkSamples...");
    }

    // Now inform each thread we are done and wait for them to be done.
    ThreadInformation.stopThreads(threadList);
  }
}
