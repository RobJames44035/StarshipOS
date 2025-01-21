/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/MethodExit/mexit002.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI event callback function MethodExit.
 *     The test checks the following:
 *       - if clazz, method and frame parameters of the function
 *         contain expected values for events generated upon exit
 *         from Java and native methods.
 *       - if GetFrameLocation indentifies the executable location
 *         in the returning method, immediately prior to the return.
 *     The test is the same as mexit01 one. The only difference is
 *     the METHOD_EXIT event enable is moved from method chain()
 *     to method check().
 * COMMENTS
 *     Ported from JVMDI.
 *     Fixed the 5004632 bug.
 *
 * @library /test/lib
 * @compile mexit02a.jasm
 * @compile mexit02.java
 * @run main/othervm/native -agentlib:mexit02 mexit02
 */



public class mexit02 {

    static {
        System.loadLibrary("mexit02");
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
}
