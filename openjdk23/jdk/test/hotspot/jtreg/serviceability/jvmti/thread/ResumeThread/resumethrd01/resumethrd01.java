/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/ResumeThread/resumethrd001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     This JVMTI test exercises JVMTI thread function ResumeThread().
 *     This tests checks that for thread resumed by ResumeThread()
 *     function GetThreadState() does not return JVMTI_THREAD_STATE_SUSPENDED.
 * COMMENTS
 *     Modified due to fix of the RFE
 *     5001769 TEST_RFE: remove usage of deprecated GetThreadStatus function
 *
 * @library /test/lib
 * @run main/othervm/native -agentlib:resumethrd01=-waittime=5 resumethrd01
 */

import jdk.test.lib.jvmti.DebugeeClass;


public class resumethrd01 extends DebugeeClass {

    // load native library if required
    static {
        System.loadLibrary("resumethrd01");
    }

    // run test from command line
    public static void main(String argv[]) throws Exception {
        new resumethrd01().runIt();
    }

    /* =================================================================== */

    long timeout = 0;
    int status = DebugeeClass.TEST_PASSED;

    // run debuggee
    public void runIt() throws Exception {
        timeout = 60 * 1000; // milliseconds

        // create tested thread
        TestedThread thread = new TestedThread("TestedThread");

        // run tested thread
        System.out.println("Staring tested thread");
        try {
            thread.start();
            thread.checkReady();

            // testing sync
            System.out.println("Sync: thread started");
            status = checkStatus(status);
        } finally {
            // let thread to finish
            thread.letFinish();
            // wait for thread to finish
            System.out.println("Finishing tested thread");
            thread.join();
        }

        // testing sync
        System.out.println("Sync: thread finished");
        status = checkStatus(status);

        if (status !=0) {
            throw new RuntimeException("status = " + status);
        }
    }
}

/* =================================================================== */

// basic class for tested threads
class TestedThread extends Thread {
    private volatile boolean threadReady = false;
    private volatile boolean shouldFinish = false;

    // make thread with specific name
    public TestedThread(String name) {
        super(name);
    }

    // run thread continuously
    public void run() {
        // run in a loop
        threadReady = true;
        int i = 0;
        int n = 1000;
        while (!shouldFinish) {
            if (n <= 0) {
                n = 1000;
            }
            if (i > n) {
                i = 0;
                n = n - 1;
            }
            i = i + 1;
        }
    }

    // check if thread is ready
    public void checkReady() {
        try {
            while (!threadReady) {
                sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interruption while preparing tested thread: \n\t" + e);
        }
    }

    // let thread to finish
    public void letFinish() {
        shouldFinish = true;
    }
}
