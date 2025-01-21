/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @bug 8222072
 * @summary Send CompiledMethodLoad events only to the environment requested it with GenerateEvents
 * @requires vm.jvmti
 * @compile GenerateEventsTest.java
 * @run main/othervm/native -agentlib:GenerateEvents1 -agentlib:GenerateEvents2 MyPackage.GenerateEventsTest
 */

package MyPackage;

public class GenerateEventsTest {
  static native void agent1GenerateEvents();
  static native void agent2SetThread(Thread thread);
  static native boolean agent1FailStatus();
  static native boolean agent2FailStatus();

  public static void main(String[] args) {
      agent2SetThread(Thread.currentThread());
      agent1GenerateEvents(); // Re-generate CompiledMethodLoad events
      if (agent1FailStatus()|| agent2FailStatus()) {
         throw new RuntimeException("GenerateEventsTest failed!");
      }
      System.out.println("GenerateEventsTest passed!");
  }
}
