/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ThreadDeathEvent.thread;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


// This class is the debugged application in the test

class thread001a {
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int JCK_STATUS_BASE = 95;

    static final String COMMAND_READY = "ready";
    static final String COMMAND_QUIT = "quit";
    static final String COMMAND_GO = "go";
    static final String COMMAND_DONE = "done";

    static int result;
    static volatile boolean mainExited = false;

    public static ArgumentHandler argHandler;
    public static Log log;

    public static Object threadsLock = new Object();
    public static Object mainThreadLock = new Object();

    public static Object threadsStarted = new Object();
    public static Object mainThreadCompleted = new Object();

    public static void main(String args[]) {
        argHandler = new ArgumentHandler(args);
        log = argHandler.createDebugeeLog();

        // execute the test
        thread001a _thread001a = new thread001a();
        System.exit(JCK_STATUS_BASE + _thread001a.runIt());
    }

    // perform the test
    int runIt() {
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        class InnerThread extends Thread {
             InnerThread (String name) {
                  super(name);
             }
             public void run() {
                  log.display(this.getName() + " thread started");
                  synchronized (threadsLock) {
                      threadsLock.notifyAll();
                  }
                  log.display(this.getName() + " thread completed");
             }
        }

        // create inner and outer threads
        InnerThread innerThread = new InnerThread("innerThread");
        InnerThread innerDaemon = new InnerThread("innerDaemon");
        innerDaemon.setDaemon(true);

        OuterThread outerThread = new OuterThread("outerThread");
        OuterThread outerDaemon = new OuterThread("outerDaemon");
        outerDaemon.setDaemon(true);

        // start threads and lock monitor to prevent threads from exit
        synchronized (threadsLock) {
            innerThread.start();
            innerDaemon.start();
            outerThread.start();
            outerDaemon.start();

            // wait for all threads started
            while (!(innerThread.isAlive() &&
                     innerDaemon.isAlive() &&
                     outerThread.isAlive() &&
                     outerDaemon.isAlive())) {
                 try {
                     synchronized (threadsStarted) {
                        threadsStarted.wait(1000);
                     }
                 } catch (InterruptedException e) {
                     log.complain("TEST INCOMPLETE: caught InterruptedException while waiting for threads started");
                     return FAILED;
                 }
            }

            log.display("All checked threads started in debuggee.");

            // notify debugger that debuggee started and ready for testing
            pipe.println(COMMAND_READY);

            // wait for command <GO> from debuggee
            String command = pipe.readln();
            if (!command.equals(COMMAND_GO)) {
                 log.complain("TEST BUG: unexpected command: " + command);
                 return FAILED;
            }

            // release lock to permit thread to complete
        }

        // wait for all threads completed
        while (innerThread.isAlive() ||
               innerDaemon.isAlive() ||
               outerThread.isAlive() ||
               outerDaemon.isAlive()) {
             try {
                 synchronized (threadsLock) {
                    threadsLock.wait(1000);
                 }
             } catch (InterruptedException e) {
                 log.complain("TEST INCOMPLETE: caught InterruptedException while waiting for threads completed");
                 return FAILED;
             }
        }

        log.display("All checked threads completed in debuggee.");

        // notify debugger that all threads completed
        pipe.println(COMMAND_DONE);

        // wait for command <QUIT> from debuggee
        String command = pipe.readln();
        if (!command.equals(COMMAND_QUIT)) {
             log.complain("TEST BUG: unexpected command: " + command);
             return FAILED;
        }

        return PASSED;
    }
}

class OuterThread extends Thread {
     OuterThread (String name) {
          super(name);
     }
     public void run() {
          thread001a.log.display(this.getName() + " thread started");
          synchronized (thread001a.threadsLock) {
             thread001a.threadsLock.notifyAll();
          }
          thread001a.log.display(this.getName() + " thread completed");
     }
}
