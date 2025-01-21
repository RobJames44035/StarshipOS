/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/FieldAccess/fieldacc003.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercise JVMTI event callback function FieldAccess.
 *     The test sets access watches on fields which are defined in
 *     superclass, then triggers access watch events on these fields
 *     and checks if clazz, method, location, field_clazz, field and
 *     object parameters the function contain the expected values.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @compile fieldacc03.java
 * @run main/othervm/native -agentlib:fieldacc03 fieldacc03
 */



public class fieldacc03 {

    static {
        System.loadLibrary("fieldacc03");
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
            fieldacc03a t = new fieldacc03a();
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
        fieldacc03a t = new fieldacc03a();
        t.run();
        result = check();
        if (result != 0) {
            throw new RuntimeException("check failed with result " + result);
        }
    }
}

class fieldacc03e {
    boolean extendsBoolean = false;
    byte extendsByte = 10;
    short extendsShort = 20;
    int extendsInt = 30;
    long extendsLong = 40;
    float extendsFloat = 0.05F;
    double extendsDouble = 0.06;
    char extendsChar = 'D';
    Object extendsObject = new Object();
    int extendsArrInt[] = {70, 80};
}

class fieldacc03a extends fieldacc03e {
    public int run() {
        int i = 0;
        if (extendsBoolean == true) i++;
        if (extendsByte == 1) i++;
        if (extendsShort == 2) i++;
        if (extendsInt == 3) i++;
        if (extendsLong == 4) i++;
        if (extendsFloat == 0.5F) i++;
        if (extendsDouble == 0.6) i++;
        if (extendsChar == 'C') i++;
        if (extendsObject == this) i++;
        if (extendsArrInt[1] == 7) i++;
        return i;
    }
}
