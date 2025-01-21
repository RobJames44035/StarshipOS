/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventRequestManager.stepRequests;

import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This is a debuggee class creating several dummy user and
 * daemon threads with own names.
 */
public class stepreq001t {
    private static ArgumentHandler argHandler;

    public static void main(String args[]) {
        System.exit(new stepreq001t().runThis(args));
    }

    private int runThis(String args[]) {
        argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        Thread  thrs[] = new Thread[stepreq001.THRDS_NUM];
        Object lockObj = new Object();
        Object readyObj = new Object();
        String cmd;

// Create several dummy threads and give them new names
        thrs[0] = Thread.currentThread();
        try {
            thrs[0].setName(stepreq001.DEBUGGEE_THRDS[0]);
        } catch (SecurityException e) {
            System.err.println("TEST FAILURE: setName: caught in debuggee "
                + e);
            pipe.println("failed");
            return stepreq001.JCK_STATUS_BASE +
                stepreq001.FAILED;
        }
// Get a monitor in order to prevent the threads from exiting
        synchronized(lockObj) {
            for (int i=1; i<stepreq001.THRDS_NUM; i++) {
                thrs[i] = JDIThreadFactory.newThread(new stepreq001a(readyObj, lockObj,
                    stepreq001.DEBUGGEE_THRDS[i]));
                if (!thrs[i].isVirtual()) {
                    thrs[i].setDaemon(stepreq001.DAEMON_THRDS[i]);
                }
                if (argHandler.verbose())
                    System.out.println("Debuggee: starting thread #"
                        + i + " \"" + thrs[i].getName() + "\"");
                synchronized(readyObj) {
                    thrs[i].start();
                    try {
                        readyObj.wait(); // wait for the thread's readiness
                    } catch (InterruptedException e) {
                        System.out.println("TEST FAILURE: Debuggee: waiting for the thread "
                            + thrs[i].toString() + ": caught " + e);
                        pipe.println("failed");
                        return stepreq001.JCK_STATUS_BASE +
                            stepreq001.FAILED;
                    }
                }
                if (argHandler.verbose())
                    System.out.println("Debuggee: the thread #"
                        + i + " \"" + thrs[i].getName() + "\" started");
            }
// Now the debuggee is ready for testing
            pipe.println(stepreq001.COMMAND_READY);
            cmd = pipe.readln();
        }

// The debuggee exits
        for (int i=1; i<stepreq001.THRDS_NUM ; i++) {
            try {
                thrs[i].join(argHandler.getWaitTime()*60000);
                if (argHandler.verbose())
                    System.out.println("Debuggee: thread #"
                        + i + " \"" + thrs[i].getName() + "\" done");
            } catch (InterruptedException e) {
                System.err.println("Debuggee: joining the thread #"
                    + i + " \"" + thrs[i].getName() + "\": " + e);
            }
        }
        if (!cmd.equals(stepreq001.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            return stepreq001.JCK_STATUS_BASE +
                stepreq001.FAILED;
        }
        return stepreq001.JCK_STATUS_BASE +
            stepreq001.PASSED;
    }

    class stepreq001a extends NamedTask {
        private Object readyObj;
        private Object lockObj;

        stepreq001a(Object readyObj, Object obj,
                String name) {
            super(name);
            this.readyObj = readyObj;
            lockObj = obj;
        }

        public void run() {
            Thread thr = Thread.currentThread();

            synchronized(readyObj) {
                readyObj.notify(); // notify the main thread
            }
            if (argHandler.verbose())
                System.out.println("Debuggee's thread \""
                    + thr.getName() + "\": going to be blocked");
            synchronized(lockObj) {
                if (argHandler.verbose()) {
                    Thread.currentThread();
                    System.out.println("Debuggee's thread \""
                        + thr.getName() + "\": unblocked, exiting...");
                }
                return;
            }
        }
    }
}
