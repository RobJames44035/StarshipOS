/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.epsilon;

/**
 * @test TestAlwaysPretouch
 * @requires vm.gc.Epsilon
 * @summary Basic sanity test for Epsilon
 * @library /test/lib
 *
 * @run main/othervm -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestEpsilonEnabled
 */

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

public class TestEpsilonEnabled {
  public static void main(String[] args) throws Exception {
    if (!isEpsilonEnabled()) {
      throw new IllegalStateException("Debug builds should have Epsilon enabled");
    }
  }

  public static boolean isEpsilonEnabled() {
    for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
      if (bean.getName().contains("Epsilon")) {
        return true;
      }
    }
    return false;
  }
}
