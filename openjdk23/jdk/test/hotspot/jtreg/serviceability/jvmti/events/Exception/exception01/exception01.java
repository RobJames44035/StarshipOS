/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/Exception/exception001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercise JVMTI event callback function Exception.
 *     The test checks if the parameters of the function contain
 *     the expected values for the following exceptions thrown by Java methods:
 *       - custom class exception01c extending Throwable
 *       - ArithmeticException caused by division with zero divisor
 *       - IndexOutOfBoundsException caused by using out of range array index
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @compile exception01a.jasm
 * @compile exception01.java
 * @run main/othervm/native -agentlib:exception01 exception01
 */



public class exception01 {

    static {
        System.loadLibrary("exception01");
    }

    static volatile int result;
    native static int check();

    public static void main(String args[]) {
        testPlatformThread();
        testVirtualThread();
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
