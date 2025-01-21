/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.epsilon;

/**
 * @test TestRefArrays
 * @key randomness
 * @requires vm.gc.Epsilon
 * @summary Epsilon is able to allocate arrays, and does not corrupt their state
 * @library /test/lib
 *
 * @run main/othervm -XX:+UseTLAB -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestRefArrays
 *
 * @run main/othervm -XX:+UseTLAB -Xmx256m
 *                   -Xint
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestRefArrays
 *
 * @run main/othervm -XX:+UseTLAB -Xmx256m
 *                   -Xbatch -Xcomp -XX:TieredStopAtLevel=1
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestRefArrays
 *
 * @run main/othervm -XX:+UseTLAB -Xmx256m
 *                   -Xbatch -Xcomp -XX:-TieredCompilation
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestRefArrays
 *
 * @run main/othervm -XX:-UseTLAB -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestRefArrays
 *
 * @run main/othervm -XX:-UseTLAB -Xmx256m
 *                   -Xint
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestRefArrays
 *
 * @run main/othervm -XX:-UseTLAB -Xmx256m
 *                   -Xbatch -Xcomp -XX:TieredStopAtLevel=1
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestRefArrays
 *
 * @run main/othervm -XX:-UseTLAB -Xmx256m
 *                   -Xbatch -Xcomp -XX:-TieredCompilation
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestRefArrays
 */

import java.util.Random;
import jdk.test.lib.Utils;

public class TestRefArrays {
  static int COUNT = Integer.getInteger("count", 200); // ~100 MB allocation

  static MyObject[][] arr;

  public static void main(String[] args) throws Exception {
    Random r = Utils.getRandomInstance();

    arr = new MyObject[COUNT * 100][];
    for (int c = 0; c < COUNT; c++) {
      arr[c] = new MyObject[c * 100];
      for (int v = 0; v < c; v++) {
        arr[c][v] = new MyObject(r.nextInt());
      }
    }

    r = new Random(Utils.SEED);
    for (int c = 0; c < COUNT; c++) {
      MyObject[] b = arr[c];
      if (b.length != (c * 100)) {
        throw new IllegalStateException("Failure: length = " + b.length + ", need = " + (c*100));
      }
      for (int v = 0; v < c; v++) {
        int actual = b[v].id();
        int expected = r.nextInt();
        if (actual != expected) {
          throw new IllegalStateException("Failure: expected = " + expected + ", actual = " + actual);
        }
      }
    }
  }

  public static class MyObject {
    int id;
    public MyObject(int id) {
      this.id = id;
    }
    public int id() {
      return id;
    }
  }
}
