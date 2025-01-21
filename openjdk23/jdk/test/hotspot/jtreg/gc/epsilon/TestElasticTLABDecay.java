/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.epsilon;

/**
 * @test TestElasticTLABDecay
 * @key randomness
 * @requires vm.gc.Epsilon
 * @summary Epsilon is able to work with/without elastic TLABs decay
 * @library /test/lib
 *
 * @run main/othervm -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:+EpsilonElasticTLAB -XX:-EpsilonElasticTLABDecay
 *                   gc.epsilon.TestElasticTLABDecay
 *
 * @run main/othervm -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:+EpsilonElasticTLAB -XX:+EpsilonElasticTLABDecay -XX:EpsilonTLABDecayTime=1
 *                   gc.epsilon.TestElasticTLABDecay
 *
 * @run main/othervm -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:+EpsilonElasticTLAB -XX:+EpsilonElasticTLABDecay -XX:EpsilonTLABDecayTime=100
 *                   gc.epsilon.TestElasticTLABDecay
 */

import java.util.Random;
import jdk.test.lib.Utils;

public class TestElasticTLABDecay {
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
      Thread.sleep(5);
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
