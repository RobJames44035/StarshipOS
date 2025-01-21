/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @summary Verifies JVMTI support for VThreads.
 * @requires vm.continuations
 * @compile VThreadTest.java
 * @run main/othervm/native -agentlib:VThreadTest VThreadTest
 */

import java.util.concurrent.*;

public class VThreadTest {
    private static final String agentLib = "VThreadTest";

    static final int MSG_COUNT = 10*1000;
    static final SynchronousQueue<String> QUEUE = new SynchronousQueue<>();

    static native boolean check();

    static void producer(String msg) throws InterruptedException {
        int ii = 1;
        long ll = 2*(long)ii;
        float ff = ll + 1.2f;
        double dd = ff + 1.3D;
        msg += dd;
        QUEUE.put(msg);
    }

    static final Runnable PRODUCER = () -> {
        try {
            for (int i = 0; i < MSG_COUNT; i++) {
                producer("msg: ");
            }
        } catch (InterruptedException e) { }
    };

    static final Runnable CONSUMER = () -> {
        try {
            for (int i = 0; i < MSG_COUNT; i++) {
                String s = QUEUE.take();
            }
        } catch (InterruptedException e) { }
    };

    public static void test1() throws Exception {
        Thread producer = Thread.ofVirtual().name("VThread-Producer").start(PRODUCER);
        Thread consumer = Thread.ofVirtual().name("VThread-Consumer").start(CONSUMER);
        producer.join();
        consumer.join();
        if (!check()) {
            throw new RuntimeException("VThreadTest failed!");
        }
    }

    void runTest() throws Exception {
        test1();
    }

    public static void main(String[] args) throws Exception {
        try {
            System.loadLibrary(agentLib);
        } catch (UnsatisfiedLinkError ex) {
            System.err.println("Failed to load " + agentLib + " lib");
            System.err.println("java.library.path: " + System.getProperty("java.library.path"));
            throw ex;
        }

        VThreadTest obj = new VThreadTest();
        obj.runTest();
    }
}
