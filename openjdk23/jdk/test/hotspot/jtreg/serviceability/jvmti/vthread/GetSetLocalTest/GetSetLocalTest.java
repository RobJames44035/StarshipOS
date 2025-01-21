/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @summary Verifies JVMTI GetLocalXXX/SetLocalXXX support for virtual threads.
 * @requires vm.continuations
 * @library /test/lib
 * @compile GetSetLocalTest.java
 * @run main/othervm/native -agentlib:GetSetLocalTest GetSetLocalTest
 */

import java.util.concurrent.*;

public class GetSetLocalTest {
    private static final String agentLib = "GetSetLocalTest";

    static SynchronousQueue<String> QUEUE;
    static native boolean completed();
    static native void enableEvents(Thread thread);
    static native void testSuspendedVirtualThreads(Thread thread);
    static Thread producer;
    static Thread consumer;

    static void producer(String msg) throws InterruptedException {
        Thread tt = Thread.currentThread();
        int ii = 1;
        long ll = 2*(long)ii;
        float ff = ll + 1.2f;
        double dd = ff + 1.3D;

        msg += dd;
        QUEUE.put(msg);
    }

    static final Runnable PRODUCER = () -> {
        try {
            while (!completed()) {
                producer("msg: ");
            }
            consumer.interrupt();
        } catch (InterruptedException e) {
        }
    };

    static final Runnable CONSUMER = () -> {
        try {
            while(true) {
                String s = QUEUE.take();
            }
        } catch (InterruptedException e) {
            System.err.println("CONSUMER was interrupted!");
        }
    };

    public static void test1() throws Exception {
        QUEUE = new SynchronousQueue<>();
        producer = Thread.ofVirtual().name("VThread-Producer").start(PRODUCER);
        consumer = Thread.ofVirtual().name("VThread-Consumer").start(CONSUMER);

        testSuspendedVirtualThreads(producer);
        enableEvents(producer);

        producer.join();
        consumer.join();
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

        GetSetLocalTest obj = new GetSetLocalTest();

        for (int i = 0; i < 200; i++) {
            obj.runTest();
        }

    }
}
