/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdb.where.where006;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class where006a {
    public static void main(String args[]) {
        where006a _where006a = new where006a();
        System.exit(where006.JCK_STATUS_BASE + _where006a.runIt(args, System.out));
    }

    static void lastBreak () {}

    static int numThreads = 5;   // number of threads. one lock per thread.

    static Object lock = new Object();
    static Object waitnotify = new Object();

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        Thread holder [] = new Thread[numThreads];
        Lock locks[] = new Lock[numThreads];

        for (int i = 0; i < numThreads ; i++) {
           locks[i] = new Lock();
           holder[i] = new MyThread(locks[i],"MyThread-" + i);
        }

        // lock monitor to prevent threads from finishing after they started
        synchronized (lock) {
           synchronized (waitnotify) {
              for (int i = 0; i < numThreads ; i++) {
                 holder[i].start();
                 try {
                     waitnotify.wait();
                 } catch ( Exception e ) {
                     System.err.println("TEST ERROR: caught Exception while waiting: " + e);
                     e.printStackTrace();
                 }
              }
           }
           lastBreak();   // dummy breakpoint
        }

        for (int i = 0; i < numThreads ; i++) {
            if (holder[i].isAlive()) {
                try {
                    holder[i].join(argumentHandler.getWaitTime() * 60000);
                } catch (InterruptedException e) {
                    throw new Failure("Unexpected InterruptedException catched while waiting for join of: " + holder[i]);
                }
            }
        }

        log.display("Debuggee PASSED");
        return where006.PASSED;
    }

}

class Lock {
   boolean lockSet;

   synchronized void setLock() throws InterruptedException {
      while (lockSet == true )
         wait();
      lockSet = true;
   }

   synchronized void releaseLock() {
      if (lockSet == true) {
        lockSet = false;
        notify();
      }
   }
}

class MyThread extends Thread {
   Lock lock;
   String name;
   MyThread(Lock l, String n) {this.lock = l; name = n;}

   public void run() {
      // Concatenate strings in advance to avoid lambda calculations later
      final String ThreadFinished = "Thread finished: " + this.name;
      int square = func1(10);
      lock.releaseLock();
      System.out.println(ThreadFinished);
   }

   public int func1(int i) {
      return func2(i);
   }

   public int func2(int i) {
      return func3(i);
   }

   public int func3(int i) {
      return func4(i);
   }

   public int func4(int i) {
      return func5(i);
   }

   public int func5(int i) {
      synchronized (where006a.waitnotify) {
         where006a.waitnotify.notify();
      }
      // prevent thread for early finish
      synchronized (where006a.lock) {}
      return i*i;
   }
}
