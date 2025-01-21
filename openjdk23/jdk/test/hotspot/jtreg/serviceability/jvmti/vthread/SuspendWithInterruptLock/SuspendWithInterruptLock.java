/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test id=default
 * @summary Do not suspend virtual threads in a critical section.
 * @bug 8311218
 * @requires vm.continuations
 * @library /testlibrary
 * @run main/othervm SuspendWithInterruptLock
 */

/**
 * @test id=xint
 * @requires vm.debug != true
 * @requires vm.continuations
 * @library /testlibrary
 * @run main/othervm -Xint SuspendWithInterruptLock
 */

/**
 * @test id=xint-debug
 * @requires vm.debug == true
 * @requires vm.continuations
 * @library /testlibrary
 * @run main/othervm -Xint -XX:-VerifyContinuations SuspendWithInterruptLock
 */

import jvmti.JVMTIUtils;

public class SuspendWithInterruptLock {
    static volatile boolean done;

    public static void main(String[] args) throws Exception {
        Thread yielder = Thread.ofVirtual().name("yielder").start(() -> yielder());
        Thread stateReader = Thread.ofVirtual().name("stateReader").start(() -> stateReader(yielder));
        Thread suspender = new Thread(() -> suspender(stateReader));
        suspender.start();

        yielder.join();
        stateReader.join();
        suspender.join();
    }

    static private void yielder() {
        int iterations = 100_000;
        while (iterations-- > 0) {
            Thread.yield();
        }
        done = true;
    }

    static private void stateReader(Thread target) {
        while (!done) {
            target.getState();
        }
    }

    static private void suspender(Thread target) {
        while (!done) {
            suspendThread(target);
            sleep(1);
            resumeThread(target);
            // Allow progress
            sleep(5);
        }
    }

    static void suspendThread(Thread t) {
        try {
            JVMTIUtils.suspendThread(t);
        } catch (JVMTIUtils.JvmtiException e) {
            if (e.getCode() != JVMTIUtils.JVMTI_ERROR_THREAD_NOT_ALIVE) {
                throw e;
            }
        }
    }

    static void resumeThread(Thread t) {
        try {
            JVMTIUtils.resumeThread(t);
        } catch (JVMTIUtils.JvmtiException e) {
            if (e.getCode() != JVMTIUtils.JVMTI_ERROR_THREAD_NOT_ALIVE) {
                throw e;
            }
        }
    }

    static private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }
}

