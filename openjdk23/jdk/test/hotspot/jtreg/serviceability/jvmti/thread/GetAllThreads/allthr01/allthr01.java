/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/GetAllThreads/allthr001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI function GetAllThreads.
 *     The test cases include:
 *     - platform, virtual and native debug threads
 *     - running and dead threads
 * COMMENTS
 *     Fixed according to the 4480280 bug.
 *     Ported from JVMDI.
 *
 * @requires vm.continuations
 * @library /test/lib
 * @compile allthr01.java
 * @run main/othervm/native -Djdk.virtualThreadScheduler.maxPoolSize=1 -agentlib:allthr01 allthr01
 */

import java.util.concurrent.atomic.AtomicBoolean;

public class allthr01 {

    static {
        System.loadLibrary("allthr01");
    }

    // Sync with native code
    final static String THREAD_NAME = "thread1";

    native static void startAgentThread();
    native static void stopAgentThread();
    native static boolean checkInfo0(int expectedInfoIdx);
    static void checkInfo(int expectedInfoIdx) {
        if (!checkInfo0(expectedInfoIdx)) {
            throw new RuntimeException("checkInfo failed for idx: " + expectedInfoIdx);
        }
    }

    public static void main(String[] args) {
        checkInfo(0);

        MyThread platformThread = new MyThread(THREAD_NAME, false);
        checkInfo(1);

        platformThread.start();

        checkInfo(2);

        platformThread.finish();
        checkInfo(3);

        startAgentThread();
        checkInfo(4);
        stopAgentThread();

        MyThread virtualThread = new MyThread(THREAD_NAME, true);
        virtualThread.start();
        checkInfo(5);
        virtualThread.finish();
    }
}

class MyThread implements Runnable {
    private final Thread thread;
    private final AtomicBoolean isStarted = new AtomicBoolean(false);
    private final AtomicBoolean shouldStop = new AtomicBoolean(false);

    MyThread(String name, boolean isVirtual) {
        if (isVirtual) {
            thread = Thread.ofVirtual().name(name).unstarted(this);
        } else {
            ThreadGroup tg = new ThreadGroup("tg1");
            thread = Thread.ofPlatform().name(name).group(tg).daemon(true).unstarted(this);
        }
    }

    void start() {
        thread.start();
        waitUntilThreadIsStarted();
    }

    @Override
    public void run() {
        isStarted.set(true);
        while(!shouldStop.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void waitUntilThreadIsStarted() {
        while (!isStarted.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void finish() {
        try {
            shouldStop.set(true);
            thread.join();
        } catch (InterruptedException e) {
            throw new Error("Unexpected " + e);
        }
    }
}
