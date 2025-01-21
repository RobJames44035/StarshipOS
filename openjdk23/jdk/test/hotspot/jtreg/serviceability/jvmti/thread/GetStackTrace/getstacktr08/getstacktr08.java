/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/GetStackTrace/getstacktr008.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI function GetStackTrace.
 *     The test starts a new thread, does some nested calls with a native
 *     call in the middle, and stops at breakpoint.
 *     Then the test does the following:
 *         - checks the stack on expected frames
 *         - steps
 *         - checks the stack on expected frames
 *         - pops frame
 *         - checks the stack on expected frames
 *         - redefines class
 *         - checks the stack on expected frames
 *         - checks the stack on expected frames just before
 *           returning from the native call.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @requires vm.continuations
 * @library /test/lib
 * @compile getstacktr08.java
 * @run main/othervm/native -agentlib:getstacktr08 getstacktr08
 */


import java.io.File;
import java.io.InputStream;

public class getstacktr08 {

    final static String fileName =
        TestThread.class.getName().replace('.', File.separatorChar) + ".class";

    static {
        System.loadLibrary("getstacktr08");
    }

    native static void getReady(Class clz, byte bytes[]);
    native static void nativeChain(Class clz);

    public static void main(String args[]) throws Exception {
        ClassLoader cl = getstacktr08.class.getClassLoader();
        Thread thread = Thread.ofPlatform().unstarted(new TestThread());

        InputStream in = cl.getSystemResourceAsStream(fileName);
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        in.close();

        getReady(TestThread.class, bytes);

        thread.start();
        thread.join();
        /* PopFrame not implemented for virtual threads yet.
        Thread vThread = Thread.ofVirtual().unstarted(new TestThread());
        getReady(TestThread.class, bytes);
        vThread.start();
        vThread.join();
        */

    }

    static class TestThread implements Runnable {
        public void run() {
            chain1();
        }

        static void chain1() {
            chain2();
        }

        static void chain2() {
            chain3();
        }

        static void chain3() {
            nativeChain(TestThread.class);
        }

        static void chain4() {
            chain5();
        }
        static void chain5() {
            checkPoint();
        }

        // dummy method to be breakpointed in agent
        static void checkPoint() {
        }
    }
}
