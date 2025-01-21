/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 4446672
 *
 * @summary converted from VM Testbase jit/regression/b4446672.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.regression.b4446672.b4446672
 */

package jit.regression.b4446672;

import nsk.share.TestFailure;

public class b4446672 {

  public static void main(String[] args) {
    new b4446672().run();
  }

  private void run() {
    new GCThread().start();
    new TestThreadStarter().start();

    while (!gcing) Thread.yield();
    while (!starting) Thread.yield();
      done = true;
    while (!testing) Thread.yield();
  }

  class TestThread extends Thread {
    public void run() {
      System.out.println ("TestThread.");
      testing = true;
    }
  }

  class TestThreadStarter extends Thread {
    public void run() {
      System.out.println ("TestThreadStarter.");
      starting=true;
      testThread.start();
    }
  }

  class GCThread extends Thread {
    public void run() {
      System.out.println ("GCThread run.");
      synchronized (testThread) {
        System.out.println ("GCThread synchronized.");
              while (!done) {
                gcing=true;
                Thread.yield();
                System.gc();
              }
            }
        System.out.println ("GCThread done.");
    }
  }

  TestThread testThread = new TestThread();
  boolean done = false;
  boolean gcing = false;
  boolean testing = false;
  boolean starting = false;
}
