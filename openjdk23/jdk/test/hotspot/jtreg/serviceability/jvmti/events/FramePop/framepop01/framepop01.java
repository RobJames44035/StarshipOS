/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/FramePop/framepop001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI event callback function FramePop.
 *     The test checks the following:
 *       - if clazz, method and frame parameters contain expected values
 *         for event generated upon exit from single method in single frame
 *         specified in call to NotifyFramePop.
 *       - if GetFrameLocation identifies the executable location
 *         in the returning method, immediately prior to the return.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @compile framepop01a.java
 * @run main/othervm/native -agentlib:framepop01 framepop01
 */

public class framepop01 {

    static {
        System.loadLibrary("framepop01");
    }

    static volatile int result;
    native static int check();

    public static void main(String args[]) {
        testVirtualThread();
        testPlatformThread();
    }
    public static void testVirtualThread() {
        Thread thread = Thread.startVirtualThread(() -> {
            result = check();
        });
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (result != 0) {
            throw new RuntimeException("check failed with result " + result);
        }
    }
    public static void testPlatformThread() {
        result = check();
        if (result != 0) {
            throw new RuntimeException("check failed with result " + result);
        }
    }

    public static void chain() {
    }
}
