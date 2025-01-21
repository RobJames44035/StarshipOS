/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @summary Verifies JVMTI GetStackTrace functions called after vthread is suspended.
 * @library /test/lib
 * @compile GetStackTraceSuspendedStressTest.java
 * @run main/othervm/native -agentlib:GetStackTraceSuspendedStress GetStackTraceSuspendedStressTest
 */

import jdk.test.lib.jvmti.DebugeeClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GetStackTraceSuspendedStressTest extends DebugeeClass {
    private static final String agentLib = "GetStackTraceSuspendedStress";

    static final int MSG_COUNT = 1000;
    static final int VTHREAD_COUNT = 60;
    static final SynchronousQueue<String> QUEUE = new SynchronousQueue<>();

    static void producer(String msg) throws InterruptedException {
        int ii = 1;
        long ll = 2*(long)ii;
        float ff = ll + 1.2f;
        double dd = ff + 1.3D;
        msg += dd;
        QUEUE.put(msg);
    }

    static void producer() {
        try {
            for (int i = 0; i < MSG_COUNT; i++) {
                producer("msg: ");
            }
        } catch (InterruptedException e) { }
    }

    static void consumer() {
        try {
            for (int i = 0; i < MSG_COUNT; i++) {
                String s = QUEUE.take();
            }
        } catch (InterruptedException e) { }
    }

    static String threadName() {
        return Thread.currentThread().getName();
    }

    static final Runnable PRODUCER = () -> {
        String name = threadName();

        System.out.println(name + ": started");
        producer();
        System.out.println(name + ": finished");
    };

    static final Runnable CONSUMER = () -> {
        String name = threadName();

        System.out.println(name + ": started");
        consumer();
        System.out.println(name + ": finished");
    };

    public static void test1() throws Exception {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < VTHREAD_COUNT; i++) {
            threads.add(Thread.ofVirtual().name("VThread-Producer-" + i).unstarted(PRODUCER));
            threads.add(Thread.ofVirtual().name("VThread-Consumer-" + i).unstarted(CONSUMER));
        }

        for (Thread t: threads) {
            t.start();
        }

        for (Thread t: threads) {
            t.join();
        }
    }

    void runTest() throws Exception {
        // sync point to start agent thread
        checkStatus(0);
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

        GetStackTraceSuspendedStressTest obj = new GetStackTraceSuspendedStressTest();
        obj.runTest();
    }
}
