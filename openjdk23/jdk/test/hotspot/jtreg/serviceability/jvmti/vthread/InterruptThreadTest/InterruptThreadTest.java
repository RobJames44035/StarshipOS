/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test id=default
 * @summary Verifies JVMTI InterruptThread works for virtual threads.
 * @compile InterruptThreadTest.java
 * @run main/othervm/native -agentlib:InterruptThreadTest InterruptThreadTest
 */

/*
 * @test id=no-vmcontinuations
 * @requires vm.continuations
 * @compile InterruptThreadTest.java
 * @run main/othervm/native -agentlib:InterruptThreadTest -XX:+UnlockExperimentalVMOptions -XX:-VMContinuations InterruptThreadTest
 */

import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptThreadTest {
    private static final String AGENT_LIB = "InterruptThreadTest";
    final Object lock = new Object();

    native boolean testJvmtiFunctionsInJNICall(Thread vthread);

    volatile private boolean target_is_ready = false;
    private boolean iterrupted = false;

    final Runnable pinnedTask = () -> {
        synchronized (lock) {
            try {
                target_is_ready = true;
                lock.wait();
            } catch (InterruptedException ie) {
                 System.err.println("Virtual thread was interrupted as expected");
                 iterrupted = true;
            }
        }
    };

    void runTest() throws Exception {
        Thread vthread = Thread.ofVirtual().name("VThread").start(pinnedTask);

        // wait for target virtual thread to reach the expected waiting state
        while (!target_is_ready) {
           synchronized (lock) {
              lock.wait(1);
            }
        }
        testJvmtiFunctionsInJNICall(vthread);
        vthread.join();
        if (!iterrupted) {
            throw new RuntimeException("Failed: Virtual thread was not interrupted!");
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            System.loadLibrary(AGENT_LIB);
        } catch (UnsatisfiedLinkError ex) {
            System.err.println("Failed to load " + AGENT_LIB + " lib");
            System.err.println("java.library.path: " + System.getProperty("java.library.path"));
            throw ex;
        }
        InterruptThreadTest t = new InterruptThreadTest();
        t.runTest();
    }
}
