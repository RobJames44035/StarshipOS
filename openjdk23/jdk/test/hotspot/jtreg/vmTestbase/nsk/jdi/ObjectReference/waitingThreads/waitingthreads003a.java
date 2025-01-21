/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ObjectReference.waitingThreads;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * The debugged applcation of the test.
 */
public class waitingthreads003a {

    //------------------------------------------------------- immutable common fields

    private static int exitStatus;
    private static ArgumentHandler argHandler;
    private static Log log;
    private static IOPipe pipe;

    //------------------------------------------------------- immutable common methods

    static void display(String msg) {
        log.display("debuggee > " + msg);
    }

    static void complain(String msg) {
        log.complain("debuggee FAILURE > " + msg);
    }

    public static void receiveSignal(String signal) {
        String line = pipe.readln();

        if ( !line.equals(signal) )
            throw new Failure("UNEXPECTED debugger's signal " + line);

        display("debuger's <" + signal + "> signal received.");
    }

    //------------------------------------------------------ mutable common fields

    //------------------------------------------------------ test specific fields

    static Object waitnotifyObj = new Object();
    static waitingthreads003aLock lockingObject = new waitingthreads003aLock();
    static final int threadCount = 5;
    static final String threadNamePrefix = "MyThread-";
    static waitingthreads003aThread[] threads = new waitingthreads003aThread[threadCount];

    //------------------------------------------------------ mutable common method

    public static void main (String argv[]) {

        exitStatus = Consts.TEST_PASSED;
        argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);
        long waitTime = argHandler.getWaitTime() * 60000;

        pipe.println(waitingthreads003.SIGNAL_READY);

        try {
            synchronized (lockingObject) {
                display("entered: synchronized (lockingObject) {}");
                synchronized (waitnotifyObj) {
                    display("entered: synchronized (waitnotifyObj) {}");

                    for (int i = 0; i < threadCount; i++) {
                        threads[i] = new waitingthreads003aThread(threadNamePrefix + i);
                        threads[i].start();
                        try {
                            waitnotifyObj.wait();
                        } catch (InterruptedException e) {
                            throw new Failure("Unexpected InterruptedException while waiting for " + threadNamePrefix + i + " start");
                        }
                    }

                    pipe.println(waitingthreads003.SIGNAL_GO);
                    receiveSignal(waitingthreads003.SIGNAL_QUIT);
                }
                display("exited: synchronized (waitnotifyObj) {}");
            }
            display("exited: synchronized (lockingObject) {}");

            for (int i = 0; i < threadCount; i++) {
                if (threads[i].isAlive()) {
                    try {
                        threads[i].join(waitTime);
                    } catch (InterruptedException e) {
                        throw new Failure("Unexpected InterruptedException while waiting for " + threadNamePrefix + i + " join");
                    }
                }
            }

//            receiveSignal(waitingthreads003.SIGNAL_QUIT);
            display("completed succesfully.");
            System.exit(exitStatus + Consts.JCK_STATUS_BASE);
        } catch (Failure e) {
            log.complain(e.getMessage());
            System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
        }
    }

    //--------------------------------------------------------- test specific methods

}

//--------------------------------------------------------- test specific classes

class waitingthreads003aThread extends Thread {
    public waitingthreads003aThread(String threadName) {
        super(threadName);
    }

    public void run() {
        synchronized (waitingthreads003a.waitnotifyObj) {
            waitingthreads003a.waitnotifyObj.notifyAll();
        }
        waitingthreads003a.lockingObject.foo();
        display("exited:  synchronized method foo");
    }

    private void display (String str) {
        waitingthreads003a.display(Thread.currentThread().getName() + " : " + str);
    }
}

class waitingthreads003aLock {
    synchronized void foo() {
        display("entered: synchronized method foo");
    }

    private void display (String str) {
        waitingthreads003a.display(Thread.currentThread().getName() + " : " + str);
    }
}
