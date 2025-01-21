/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.epsilon;

/**
 * @test TestObjects
 * @key randomness
 * @requires vm.gc.Epsilon
 * @summary Epsilon is able to allocate objects, and does not corrupt their state
 * @library /test/lib
 *
 * @run main/othervm -XX:+UseTLAB -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestObjects
 *
 * @run main/othervm -XX:+UseTLAB -Xmx256m
 *                   -Xint
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestObjects
 *
 * @run main/othervm -XX:+UseTLAB -Xmx256m
 *                   -Xbatch -Xcomp -XX:TieredStopAtLevel=1
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestObjects
 *
 * @run main/othervm -XX:+UseTLAB -Xmx256m
 *                   -Xbatch -Xcomp -XX:-TieredCompilation
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestObjects
 *
 * @run main/othervm -XX:-UseTLAB -Xmx256m
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestObjects
 *
 * @run main/othervm -XX:-UseTLAB -Xmx256m
 *                   -Xint
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestObjects
 *
 * @run main/othervm -XX:-UseTLAB -Xmx256m
 *                   -Xbatch -Xcomp -XX:TieredStopAtLevel=1
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestObjects
 *
 * @run main/othervm -XX:-UseTLAB -Xmx256m
 *                   -Xbatch -Xcomp -XX:-TieredCompilation
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestObjects
 */

import java.util.Random;
import jdk.test.lib.Utils;

public class TestObjects {
  static int COUNT = Integer.getInteger("count", 1_000_000); // ~24 MB allocation

  static MyObject[] arr;

  public static void main(String[] args) throws Exception {
    Random r = Utils.getRandomInstance();

    arr = new MyObject[COUNT];
    for (int c = 0; c < COUNT; c++) {
      arr[c] = new MyObject(r.nextInt());
    }

    r = new Random(Utils.SEED);
    for (int c = 0; c < COUNT; c++) {
      int expected = r.nextInt();
      int actual = arr[c].id();
      if (expected != actual) {
        throw new IllegalStateException("Failure: expected = " + expected + ", actual = " + actual);
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
