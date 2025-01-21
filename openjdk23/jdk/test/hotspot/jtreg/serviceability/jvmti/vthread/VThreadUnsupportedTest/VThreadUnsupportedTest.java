/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @summary Verifies that specific JVMTI functions return UNSUPPORTED_OPERATION
 *          or OPAQUE_FRAME if called with virtual threads.
 * @requires vm.continuations
 * @compile VThreadUnsupportedTest.java
 * @run main/othervm/native -agentlib:VThreadUnsupportedTest VThreadUnsupportedTest
 */

import java.util.concurrent.atomic.AtomicBoolean;

public class VThreadUnsupportedTest {
    private static final String AGENT_LIB = "VThreadUnsupportedTest";
    final Object lock = new Object();
    final AtomicBoolean isJNITestingCompleted = new AtomicBoolean(false);

    native boolean isCompletedTestInEvent();
    native boolean testJvmtiFunctionsInJNICall(Thread vthread);

    final Runnable pinnedTask = () -> {
        synchronized (lock) {
            do {
                try {
                    lock.wait(10);
                } catch (InterruptedException ie) {}
            } while (!isCompletedTestInEvent() || !isJNITestingCompleted.get());
        }
    };

    void runTest() throws Exception {
        Thread vthread = Thread.ofVirtual().name("VThread").start(pinnedTask);
        testJvmtiFunctionsInJNICall(vthread);
        isJNITestingCompleted.set(true);
        vthread.join();
    }

    public static void main(String[] args) throws Exception {
        try {
            System.loadLibrary(AGENT_LIB);
        } catch (UnsatisfiedLinkError ex) {
            System.err.println("Failed to load " + AGENT_LIB + " lib");
            System.err.println("java.library.path: " + System.getProperty("java.library.path"));
            throw ex;
        }
        VThreadUnsupportedTest t = new VThreadUnsupportedTest();
        t.runTest();
    }
}
