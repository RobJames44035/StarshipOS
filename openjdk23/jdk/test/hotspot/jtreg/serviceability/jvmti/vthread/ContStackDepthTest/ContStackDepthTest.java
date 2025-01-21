/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @summary Verifies JVMTI support for Continuations
 * @requires vm.continuations
 * @compile ContStackDepthTest.java
 * @modules java.base/jdk.internal.vm
 * @run main/othervm/native -agentlib:ContStackDepthTest ContStackDepthTest
 */

import java.math.BigInteger;
import java.math.BigInteger.*;
import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

public class ContStackDepthTest {
    private static final String agentLib = "ContStackDepthTest";
    private static ContinuationScope scope = new ContinuationScope("Fibonacci") {};
    private static Continuation cont;
    private static BigInteger value = BigInteger.ONE;
    private static boolean done = false;

    static void log(String str) { System.out.println(str); }

    static native void enableEvents(Thread thread);
    static native boolean check();

    public static void main(String[] args) throws Exception {
        try {
            System.loadLibrary(agentLib);
        } catch (UnsatisfiedLinkError ex) {
            log("Failed to load " + agentLib + " lib");
            log("java.library.path: " + System.getProperty("java.library.path"));
            throw ex;
        }
        log("\n######   main: started   #####\n");
        enableEvents(Thread.currentThread());

        ContStackDepthTest obj = new ContStackDepthTest();
        obj.runTest();

        if (!check()) {
            throw new RuntimeException(
                "ContStackDepthTest failed: miscounted FramePop, MethodEnter or MethodExit events!");
        }
        log("ContStackDepthTest passed\n");
        log("\n#####   main: finished  #####\n");
    }

    public void runTest() {
        log("\n####  runTest: started  ####\n");
        fibTest();
        log("\n####  runTest: finished ####\n");
    }

    static final Runnable FIB = () -> {
        Continuation.yield(scope);
        var cur = value;
        var next = BigInteger.ONE;
        int iter = 0;

        while (true) {
            log("\n  ##  FIB iteration: " + (++iter) + "  ##\n");
            if (done) return;

            value = next;
            Continuation.yield(scope);
            var tmp = cur.add(next);
            cur = next;
            next = tmp;
        }
    };

    public static void fibTest() {
        cont = new Continuation(scope, FIB);
        log("\n####  fibTest: started  ####\n");

        System.out.println("getNextFib returned value: " + getNextFib());
        System.out.println("getNextFib returned value: " + getNextFib());
        System.out.println("getNextFib returned value: " + getNextFib());

        done = true;

        System.out.println("getNextFib returned value: " + getNextFib());

        log("\n####  fibTest: finished ####\n");
    }

    public static BigInteger getNextFib() {
        cont.run();
        return value;
    }
}
