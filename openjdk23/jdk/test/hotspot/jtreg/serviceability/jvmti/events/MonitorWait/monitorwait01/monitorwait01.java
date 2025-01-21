/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import jdk.test.lib.jvmti.DebugeeClass;

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/MonitorWait/monitorwait001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI event callback function
 *         MonitorWait(jni, thread, object, timeout).
 *     The test checks if the thread, object, and timeout parameters of
 *     the function contain expected values for callback when a thread is
 *     about to wait on an object.
 * COMMENTS
 *     The test updated to match new JVMTI spec 0.2.90:
 *     - change signature of agentProc function
 *       and save JNIEnv pointer now passed as argument.
 *
 * @library /test/lib
 * @compile monitorwait01.java
 * @run main/othervm/native -agentlib:monitorwait01 monitorwait01 platform
 * @run main/othervm/native -agentlib:monitorwait01 monitorwait01 virtual
 */



public class monitorwait01 extends DebugeeClass {

    static {
        loadLibrary("monitorwait01");
    }

    public static void main(String args[]) {
        boolean isVirtual = "virtual".equals(args[0]);
        int result = new monitorwait01().runIt(isVirtual);
        if (result != 0) {
            throw new RuntimeException("Unexpected status: " + result);
        }
    }

    static final long timeout = 60000;

    // run debuggee
    public int runIt(boolean isVirtual) {
        int status = DebugeeClass.TEST_PASSED;
        System.out.println("Timeout = " + timeout + " msc.");

        monitorwait01Task task = new monitorwait01Task();
        Thread.Builder builder;
        if (isVirtual) {
            builder = Thread.ofVirtual();
        } else {
            builder = Thread.ofPlatform();
        }
        Thread thread = builder.name("Debuggee Thread").unstarted(task);
        setExpected(task.waitingMonitor, thread);

        // run thread
        try {
            // start thread
            synchronized (task.startingMonitor) {
                thread.start();
                task.startingMonitor.wait(timeout);
            }
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        Thread.yield();
        System.out.println("Thread started");

        synchronized (task.waitingMonitor) {
            task.waitingMonitor.notify();
        }

        // wait for thread finish
        try {
            thread.join(timeout);
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        System.out.println("Sync: thread finished");
        status = checkStatus(status);

        return status;
    }

    private native void setExpected(Object monitor, Object thread);
}

/* =================================================================== */

class monitorwait01Task implements Runnable {
    public Object startingMonitor = new Object();
    public Object waitingMonitor = new Object();

    public void run() {
        synchronized (waitingMonitor) {

            monitorwait01.checkStatus(DebugeeClass.TEST_PASSED);

            // notify about starting
            synchronized (startingMonitor) {
                startingMonitor.notify();
            }

            // wait until main thread notify
            try {
                waitingMonitor.wait(monitorwait01.timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
