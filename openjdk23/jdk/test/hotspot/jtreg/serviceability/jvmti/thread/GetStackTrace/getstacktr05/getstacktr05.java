/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/GetStackTrace/getstacktr005.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI function GetStackTrace via breakpoint
 *     and consequent step.
 *     The test starts a new thread, does some nested calls, stops at breakpoint,
 *     does single step and checks if the number of frames in the thread's
 *     stack is as expected and the function returns all the expected frames.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @requires vm.continuations
 * @library /test/lib
 * @compile getstacktr05.java
 * @run main/othervm/native -agentlib:getstacktr05 getstacktr05
 */

public class getstacktr05 {

    static {
        System.loadLibrary("getstacktr05");
    }

    native static void getReady(Class clazz);

    public static void main(String args[]) throws Exception {
        Thread thread = Thread.ofPlatform().unstarted(new TestThread());
        getReady(TestThread.class);
        thread.start();
        thread.join();

        Thread vThread = Thread.ofVirtual().unstarted(new TestThread());
        vThread.start();
        vThread.join();
    }

    static class TestThread implements Runnable {
        public void run() {
            chain1();
        }

        void chain1() {
            chain2();
        }

        void chain2() {
            chain3();
        }

        void chain3() {
            chain4();
        }

        void chain4() {
            checkPoint();
        }

        // dummy method to be breakpointed in agent
        void checkPoint() {
        }
    }
}
