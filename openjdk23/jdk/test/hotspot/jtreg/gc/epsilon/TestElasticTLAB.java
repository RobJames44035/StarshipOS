/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.epsilon;

/**
 * @test TestElasticTLAB
 * @key randomness
 * @requires vm.gc.Epsilon
 * @summary Epsilon is able to work with/without elastic TLABs
 * @library /test/lib
 *
 * @run main/othervm -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:-EpsilonElasticTLAB
 *                   gc.epsilon.TestElasticTLAB
 *
 * @run main/othervm -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:+EpsilonElasticTLAB -XX:EpsilonTLABElasticity=1
 *                   gc.epsilon.TestElasticTLAB
 *
 * @run main/othervm -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:+EpsilonElasticTLAB -XX:EpsilonTLABElasticity=1.1
 *                   gc.epsilon.TestElasticTLAB
 *
 * @run main/othervm -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:+EpsilonElasticTLAB -XX:EpsilonTLABElasticity=2.0
 *                   gc.epsilon.TestElasticTLAB
 *
 * @run main/othervm -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:+EpsilonElasticTLAB -XX:EpsilonTLABElasticity=100
 *                   gc.epsilon.TestElasticTLAB
 */

import java.util.Random;
import jdk.test.lib.Utils;

public class TestElasticTLAB {
  static int COUNT = Integer.getInteger("count", 500); // ~100 MB allocation

  static byte[][] arr;

  public static void main(String[] args) throws Exception {
    Random r = Utils.getRandomInstance();

    arr = new byte[COUNT * 100][];
    for (int c = 0; c < COUNT; c++) {
      arr[c] = new byte[c * 100];
      for (int v = 0; v < c; v++) {
        arr[c][v] = (byte)(r.nextInt(255) & 0xFF);
      }
    }

    r = new Random(Utils.SEED);
    for (int c = 0; c < COUNT; c++) {
      byte[] b = arr[c];
      if (b.length != (c * 100)) {
        throw new IllegalStateException("Failure: length = " + b.length + ", need = " + (c*100));
      }
      for (int v = 0; v < c; v++) {
        byte actual = b[v];
        byte expected = (byte)(r.nextInt(255) & 0xFF);
        if (actual != expected) {
          throw new IllegalStateException("Failure: expected = " + expected + ", actual = " + actual);
        }
      }
    }
  }
}
