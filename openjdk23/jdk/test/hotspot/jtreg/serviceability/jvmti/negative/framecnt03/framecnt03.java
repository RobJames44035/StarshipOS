/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/GetFrameCount/framecnt003.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI function GetFrameCount(thread, countPtr).
 *     The test checks if the function returns JVMTI_ERROR_INVALID_THREAD
 *     if thread is not a thread object.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @run main/othervm/native -agentlib:framecnt03 framecnt03
 */

public class framecnt03 {

    static {
        System.loadLibrary("framecnt03");
    }

    native static int check();

    public static void main(String args[]) {
        int result = check();
        if (result != 0) {
            throw new RuntimeException("check failed with result " + result);
        }
    }
}
