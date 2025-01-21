/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/GetStackTrace/getstacktr006.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI function GetStackTrace via breakpoint
 *     and consequent popping a frame.
 *     The test starts a new thread, does some nested calls, stops at breakpoint,
 *     pops frame, catches single step event and checks if the number of frames
 *     in the thread's stack is as expected and the function returns all
 *     the expected frames.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @requires vm.continuations
 * @library /test/lib
 * @compile getstacktr06.java
 * @run main/othervm/native -agentlib:getstacktr06 getstacktr06
 */

public class getstacktr06 {

    static {
        System.loadLibrary("getstacktr06");
    }

    native static void getReady(Class clazz);

    public static void main(String args[]) throws Exception{
        Thread thread = Thread.ofPlatform().unstarted(new TestThread());
        getReady(TestThread.class);
        thread.start();
        thread.join();

        /*
         PopFrame not implemented for virtual threads yet.
        Thread vThread = Thread.ofVirtual().unstarted(new TestThread());
        getReady(TestThread.class);
        vThread.start();
        vThread.join();
        */
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
