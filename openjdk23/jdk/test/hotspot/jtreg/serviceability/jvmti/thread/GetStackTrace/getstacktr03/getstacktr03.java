/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/GetStackTrace/getstacktr003.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI function GetStackTrace for a non current thread.
 *     The test checks the following:
 *       - if function returns the expected frame of a Java method
 *       - if function returns the expected frame of a JNI method
 *       - if function returns the expected number of frames.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @requires vm.continuations
 * @library /test/lib
 * @compile getstacktr03.java
 * @run main/othervm/native -agentlib:getstacktr03 getstacktr03
 */

public class getstacktr03 {

    static {
        System.loadLibrary("getstacktr03");
    }

    native static void chain();
    native static int check(Thread thread);

    public static Object lockIn = new Object();
    public static Object lockOut = new Object();

    public static void main(String args[]) {
        Thread thread = Thread.ofPlatform().unstarted(new Task());
        test(thread);

        Thread vthread = Thread.ofVirtual().unstarted(new Task());
        test(vthread);
    }

    public static void test(Thread thr) {
        synchronized (lockIn) {
            thr.start();
            try {
                lockIn.wait();
            } catch (InterruptedException e) {
                throw new Error("Unexpected " + e);
            }
        }

        synchronized (lockOut) {
            check(thr);
            lockOut.notify();
        }

        try {
            thr.join();
        } catch (InterruptedException e) {
            throw new Error("Unexpected " + e);
        }
    }

    static void dummy() {
        synchronized (lockOut) {
            synchronized (lockIn) {
                lockIn.notify();
            }
            try {
                lockOut.wait();
            } catch (InterruptedException e) {
                throw new Error("Unexpected " + e);
            }
        }
    }
    static class Task implements Runnable {
        @Override
        public void run() {
            getstacktr03.chain();
        }
    }
}
