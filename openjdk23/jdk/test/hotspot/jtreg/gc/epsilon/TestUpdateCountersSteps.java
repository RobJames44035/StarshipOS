/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.epsilon;

/**
 * @test TestUpdateCountersSteps
 * @requires vm.gc.Epsilon
 * @summary Test EpsilonUpdateCountersStep works
 *
 * @run main/othervm -Xmx64m -Xlog:gc
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:EpsilonUpdateCountersStep=1
 *                   gc.epsilon.TestUpdateCountersSteps
 *
 * @run main/othervm -Xmx64m -Xlog:gc
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:EpsilonUpdateCountersStep=10
 *                   gc.epsilon.TestUpdateCountersSteps
 *
 * @run main/othervm -Xmx64m -Xlog:gc
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:EpsilonUpdateCountersStep=100
 *                   gc.epsilon.TestUpdateCountersSteps
 *
 * @run main/othervm -Xmx64m -Xlog:gc
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:EpsilonUpdateCountersStep=1000
 *                   gc.epsilon.TestUpdateCountersSteps
 */

public class TestUpdateCountersSteps {
  public static void main(String[] args) throws Exception {
    System.out.println("Hello World");
  }
}
