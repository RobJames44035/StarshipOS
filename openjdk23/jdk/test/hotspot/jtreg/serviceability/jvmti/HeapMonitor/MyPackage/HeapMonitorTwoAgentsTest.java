/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @build Frame HeapMonitor ThreadInformation
 * @summary Verifies the JVMTI Heap Monitor does not work with two agents.
 * @requires vm.jvmti
 * @compile HeapMonitorTwoAgentsTest.java
 * @run main/othervm/native -agentlib:HeapMonitorTest MyPackage.HeapMonitorTwoAgentsTest
 */

import java.util.List;

public class HeapMonitorTwoAgentsTest {
  private native static boolean enablingSamplingInSecondaryAgent();
  private native static boolean obtainedEventsForBothAgents(Frame[] frames);

  public static void main(String[] args) {
    HeapMonitor.enableSamplingEvents();

    if (enablingSamplingInSecondaryAgent()) {
      throw new RuntimeException("Enabling sampling in second agent succeeded...");
    }
  }
}
