/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package gc;

/**
 * @test TestNUMAPageSize
 * @summary Make sure that start up with NUMA support does not cause problems.
 * @bug 8061467
 * @requires vm.gc != "Z"
 * @run main/othervm -Xmx128m -XX:+UseNUMA -XX:+UseLargePages gc.TestNUMAPageSize
 */

public class TestNUMAPageSize {
  public static void main(String args[]) throws Exception {
    // nothing to do
  }
}
