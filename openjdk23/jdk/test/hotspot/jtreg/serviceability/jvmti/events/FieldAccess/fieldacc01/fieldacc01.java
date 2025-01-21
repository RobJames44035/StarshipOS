/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/FieldAccess/fieldacc001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercise JVMTI event callback function FieldAccess.
 *     The test checks if the parameters of the function contain the
 *     expected values.
 * COMMENTS
 *     Fixed according to 4669812 bug.
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @compile fieldacc01a.jasm
 * @compile fieldacc01.java
 * @run main/othervm/native -agentlib:fieldacc01 fieldacc01
 */



public class fieldacc01 {

    static {
        System.loadLibrary("fieldacc01");
    }

    static volatile int result;
    native static void getReady();
    native static int check();

    public static void main(String args[]) {
        testPlatformThread();
        testVirtualThread();
    }
    public static void testVirtualThread() {

        Thread thread = Thread.startVirtualThread(() -> {
            getReady();
            fieldacc01a t = new fieldacc01a();
            t.run();
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
        getReady();
        fieldacc01a t = new fieldacc01a();
        t.run();
        result = check();
        if (result != 0) {
            throw new RuntimeException("check failed with result " + result);
        }
    }
}
