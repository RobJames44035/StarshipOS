/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/FieldModification/fieldmod002.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercise JVMTI event callback function FieldModification.
 *     The test checks if the parameters of the function contain the
 *     expected values for fields modified from JNI code.
 * COMMENTS
 *     Fixed according to 4669812 bug.
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @compile fieldmod02.java
 * @run main/othervm/native -agentlib:fieldmod02 fieldmod02
 */


public class fieldmod02 {

    static {
        System.loadLibrary("fieldmod02");
    }

    static volatile int result;
    native static void getReady();
    native static int check(Object obj);

    public static void main(String args[]) {
        testPlatformThread();
        testVirtualThread();
    }
    public static void testVirtualThread() {
        Thread thread = Thread.startVirtualThread(() -> {
            fieldmod02a t = new fieldmod02a();
            getReady();
            result = check(t);
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
        fieldmod02a t = new fieldmod02a();
        getReady();
        result = check(t);
        if (result != 0) {
            throw new RuntimeException("check failed with result " + result);
        }
    }
}

class fieldmod02a {
    static boolean staticBoolean;
    static byte staticByte;
    static short staticShort;
    static int staticInt;
    static long staticLong;
    static float staticFloat;
    static double staticDouble;
    static char staticChar;
    static Object staticObject;
    static int staticArrInt[];
    boolean instanceBoolean;
    byte instanceByte;
    short instanceShort;
    int instanceInt;
    long instanceLong;
    float instanceFloat;
    double instanceDouble;
    char instanceChar;
    Object instanceObject;
    int instanceArrInt[];
}
