/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test id=default
 * @bug 8284161
 * @summary Test JNI IsVirtualThread
 * @library /test/lib
 * @run main/native/othervm IsVirtualThread
 */

/*
 * @test id=no-vmcontinuations
 * @requires vm.continuations
 * @library /test/lib
 * @run main/native/othervm -XX:+UnlockExperimentalVMOptions -XX:-VMContinuations IsVirtualThread
 */

import jdk.test.lib.Asserts;

import java.util.concurrent.locks.LockSupport;

public class IsVirtualThread {
    public static void main(String[] args) throws Exception {
        test(Thread.currentThread());

        // test platform thread
        Thread thread = Thread.ofPlatform().unstarted(LockSupport::park);
        test(thread);   // not started
        thread.start();
        try {
            test(thread);   // started, probably parked
        } finally {
            LockSupport.unpark(thread);
            thread.join();
        }
        test(thread);   // terminated

        // test virtual thread
        Thread vthread = Thread.ofVirtual().unstarted(LockSupport::park);
        test(vthread);   // not started
        vthread.start();
        try {
            test(vthread);   // started, probably parked
        } finally {
            LockSupport.unpark(vthread);
            vthread.join();
        }
        test(vthread);   // terminated
    }

    private static void test(Thread thread) {
        System.out.println("test: " + thread);
        boolean isVirtual = isVirtualThread(thread);
        boolean expected = thread.isVirtual();
        Asserts.assertEQ(expected, isVirtual, "JNI IsVirtualThread() not equals to Thread.isVirtual()");
    }

    private static native boolean isVirtualThread(Thread thread);

    static {
        System.loadLibrary("IsVirtualThread");
    }
}
