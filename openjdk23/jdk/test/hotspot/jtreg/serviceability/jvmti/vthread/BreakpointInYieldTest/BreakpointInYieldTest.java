/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @summary Verifies that JVMTI function return correct info in Breakpoint events in Continuation.yield0().
 * @requires vm.continuations
 * @compile BreakpointInYieldTest.java
 * @run main/othervm/native -agentlib:BreakpointInYieldTest -Djdk.defaultScheduler.parallelism=2 BreakpointInYieldTest
 */

import java.util.concurrent.*;

public class BreakpointInYieldTest {
    private static final String agentLib = "BreakpointInYieldTest";

    static native void enableEvents(Thread thread);
    static native boolean check();

    static final int MSG_COUNT = 4;
    static final SynchronousQueue<String> QUEUE = new SynchronousQueue<>();

    static void qPut(String msg) throws InterruptedException {
        QUEUE.put(msg);
    }

    static final Runnable PRODUCER = () -> {
        enableEvents(Thread.currentThread());
        try {
            for (int i = 0; i < MSG_COUNT; i++) {
                qPut("msg" + i);
                qPut("msg" + i);
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
        Thread p1 = Thread.ofVirtual().name("VT-PRODUCER#0").start(PRODUCER);
        Thread c1 = Thread.ofVirtual().name("VT-CONSUMER#1").start(CONSUMER);
        Thread c2 = Thread.ofVirtual().name("VT-CONSUMER#2").start(CONSUMER);
        p1.join();
        c1.join();
        c2.join();
    }

    void runTest() throws Exception {
        test1();
    }

    public static void main(String[] args) throws Exception {
        try {
            System.out.println("loading " + agentLib + " lib");
            System.loadLibrary(agentLib);
        } catch (UnsatisfiedLinkError ex) {
            System.err.println("Failed to load " + agentLib + " lib");
            System.err.println("java.library.path: " + System.getProperty("java.library.path"));
            throw ex;
        }

        BreakpointInYieldTest obj = new BreakpointInYieldTest();
        obj.runTest();
        if (!check()) {
            throw new RuntimeException("BreakpointInYieldTest failed!");
        }
        System.out.println("BreakpointInYieldTest passed\n");
        System.out.println("\n#####   main: finished  #####\n");
    }
}
