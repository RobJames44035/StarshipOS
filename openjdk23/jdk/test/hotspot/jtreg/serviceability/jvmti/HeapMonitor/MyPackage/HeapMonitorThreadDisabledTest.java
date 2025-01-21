/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @build Frame HeapMonitor ThreadInformation
 * @summary Verifies the JVMTI Heap Monitor Thread can disable events for a given thread.
 * @requires vm.jvmti
 * @compile HeapMonitorThreadDisabledTest.java
 * @run main/othervm/native -Xmx512m -agentlib:HeapMonitorTest MyPackage.HeapMonitorThreadDisabledTest
 */

import java.util.List;

public class HeapMonitorThreadDisabledTest {
  private native static void enableSamplingEvents(Thread thread);
  private native static boolean checkThreadSamplesOnlyFrom(Thread thread);

  public static void main(String[] args) {
    final int numThreads = 4;
    List<ThreadInformation> threadList = ThreadInformation.createThreadList(numThreads);

    // Sample at a interval of 8k.
    HeapMonitor.setSamplingInterval(1 << 13);

    // Only enable the sampling for a given thread.
    Thread thread = threadList.get(0).getThread();
    enableSamplingEvents(thread);

    System.err.println("Starting threads");
    ThreadInformation.startThreads(threadList);
    ThreadInformation.waitForThreads(threadList);
    System.err.println("Waited for threads");

    // Only have the samples for a given thread should be captured.
    if (!checkThreadSamplesOnlyFrom(thread)) {
      throw new RuntimeException(
          "Problem with checkSamples: got no events from the expected thread");
    }

    // Now inform each thread we are done and wait for them to be done.
    ThreadInformation.stopThreads(threadList);
  }
}
