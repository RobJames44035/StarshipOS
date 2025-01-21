/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8264760
 * @summary JVM crashes when two threads encounter the same resolution error
 *
 * @compile HostNoNestMember.java
 * @compile HostNoNestMember.jcod
 *
 * @run main TestNestHostErrorWithMultiThread
 */

// HostNoNestMember.jcod must be compiled after HostNoNestMember.java
// because the class file from the jcod file must replace the
// HostNoNestMember class file generated from HostNoNestMember.java.

import java.util.concurrent.CountDownLatch;

public class TestNestHostErrorWithMultiThread {

  public static void main(String args[]) {
    CountDownLatch runLatch = new CountDownLatch(1);
    CountDownLatch startLatch = new CountDownLatch(2);

    Runnable test = new Test(runLatch, startLatch);

    Thread t1 = new Thread(test);
    Thread t2 = new Thread(test);

    t1.start();
    t2.start();

    try {
      // waiting thread creation
      startLatch.await();
      runLatch.countDown();

      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      throw new Error("Unexpected interrupt");
    }
  }

  static class Test implements Runnable {
    private CountDownLatch runLatch;
    private CountDownLatch startLatch;

    Test(CountDownLatch runLatch, CountDownLatch startLatch) {
      this.runLatch = runLatch;
      this.startLatch = startLatch;
    }

    @Override
    public void run() {
      try {
        startLatch.countDown();
        // Try to have all threads trigger the nesthost check at the same time
        runLatch.await();
        HostNoNestMember h = new HostNoNestMember();
        h.test();
        throw new Error("IllegalAccessError was not thrown as expected");
      } catch (IllegalAccessError expected) {
        String msg = "current type is not listed as a nest member";
        if (!expected.getMessage().contains(msg)) {
          throw new Error("Wrong " + expected.getClass().getSimpleName() +": \"" +
                          expected.getMessage() + "\" does not contain \"" +
                          msg + "\"", expected);
        }
        System.out.println("OK - got expected exception: " + expected);
      } catch (InterruptedException e) {
        throw new Error("Unexpected interrupt");
      }
    }
  }
}
