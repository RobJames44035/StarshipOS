/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/GetStackTrace/getstacktr001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI function GetStackTrace for the current thread.
 *     The test checks the following:
 *       - if function returns the expected frame of a Java method
 *       - if function returns the expected frame of a JNI method
 *       - if function returns the expected number of expected_frames.
 *     Test verifies stack trace for platform and virtual threads.
 *     Test doesn't check stacktrace of JDK implementation, only checks 1st JDK frame.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @compile GetStackTraceCurrentThreadTest.java
 * @run main/othervm/native -agentlib:GetStackTraceCurrentThreadTest GetStackTraceCurrentThreadTest
 */

public class GetStackTraceCurrentThreadTest {

    static {
        System.loadLibrary("GetStackTraceCurrentThreadTest");
    }

    native static void chain();
    native static void check(Thread thread);

    public static void main(String args[]) throws Exception {
        Thread.ofPlatform().start(new Task()).join();
        Thread.ofVirtual().start(new Task()).join();
    }

    public static void dummy() {
        check(Thread.currentThread());
    }

}

class Task implements Runnable {
    @Override
    public void run() {
        GetStackTraceCurrentThreadTest.chain();
    }
}
