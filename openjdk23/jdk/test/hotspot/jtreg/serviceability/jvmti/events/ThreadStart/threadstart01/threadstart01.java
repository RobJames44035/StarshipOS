/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/ThreadStart/threadstart001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, quarantine]
 * VM Testbase comments: 8016181
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI event callback function ThreadStart.
 *     The test checks if the event is ganerated by a new
 *     thread before its initial method executes.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @run main/othervm/native -agentlib:threadstart01 threadstart01
 */

public class threadstart01 {

    final static int THREADS_LIMIT = 100;
    final static String NAME_PREFIX = "threadstart01-";

    static {
        System.loadLibrary("threadstart01");
    }

    native static void getReady(int i, String name);
    native static int check();

    static volatile int thrCount = 0;

    public static void main(String args[]) {
        TestThread t = new TestThread(NAME_PREFIX + thrCount);
        getReady(THREADS_LIMIT, NAME_PREFIX);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new Error("Unexpected: " + e);
        }
        int result = check();
        if (result != 0) {
            throw new RuntimeException("Unexpected status: " + result);
        }
    }

    static class TestThread extends Thread {
        public TestThread(String name) {
            super(name);
        }
        public void run() {
            thrCount++;
            if (thrCount < THREADS_LIMIT) {
                TestThread t = new TestThread(NAME_PREFIX + thrCount);
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    throw new Error("Unexpected: " + e);
                }
            }
        }
    }
}
