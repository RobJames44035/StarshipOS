/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary Test verifies set/get TLS data and verifies it's consistency.
 * Test set TLS with thread name which it belongs to and verify this information when getting test.
 *  -- cbThreadStart
 *  -- by AgentThread
 *
 * Test doesn't verify that TLS is not NULL because for some threads TLS is not initialized initially.
 * TODO:
 *  -- verify that TLS is not NULL (not possible to do with jvmti, ThreadStart might be called too late)
 *  -- add more events where TLS is set *first time*, it is needed to test lazily jvmtThreadState init
 *  -- set/get TLS from other JavaThreads (not from agent and current thread)
 *  -- set/get for suspened (blocked?) threads
 *  -- split test to "sanity" and "stress" version
 *  -- update properties to run jvmti stress tests non-concurrently?
 *
 *
 * @library /test/lib
 * @compile SetGetThreadLocalStorageStressTest.java
 * @run main/othervm/native -agentlib:SetGetThreadLocalStorageStress SetGetThreadLocalStorageStressTest
 */


import jdk.test.lib.jvmti.DebugeeClass;

import java.util.ArrayList;
import java.util.concurrent.locks.LockSupport;


public class SetGetThreadLocalStorageStressTest extends DebugeeClass {

    static final int DEFAULT_ITERATIONS = 10;

    static {
        System.loadLibrary("SetGetThreadLocalStorageStress");
    }

    static int status = DebugeeClass.TEST_PASSED;

    public static void main(String argv[]) throws InterruptedException {
        int size = DEFAULT_ITERATIONS;
        int platformThreadNum = Runtime.getRuntime().availableProcessors() / 4;
        int virtualThreadNum = Runtime.getRuntime().availableProcessors();

        if (platformThreadNum == 0) {
            platformThreadNum = 2;
        }

        if (argv.length > 0) {
            size = Integer.parseInt(argv[0]);
        }

        // need to sync start with agent thread only when main is started
        checkStatus(status);

        long uniqID = 0;
        for (int c = 0; c < size; c++) {
            ArrayList<Thread> threads = new ArrayList<>(platformThreadNum + virtualThreadNum);
            for (int i = 0; i < platformThreadNum; i++) {
                TaskMonitor task = new TaskMonitor();
                threads.add(Thread.ofPlatform()
                        .name("PlatformThread-" + uniqID++)
                        .unstarted(task));
            }

            for (int i = 0; i < virtualThreadNum; i++) {
                TaskMonitor task = new TaskMonitor();
                threads.add(Thread.ofVirtual()
                        .name("VirtualThread-" + uniqID++)
                        .unstarted(task));
            }

            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                t.join();
            }
        }
    }
}


class TaskMonitor implements Runnable {

    public void run() {
        LockSupport.parkNanos(1);
    }
}
