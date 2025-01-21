/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/GetAllThreads/allthr002.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI function GetAllThreads(threadsCountPtr, threadsPtr)
 *     The test checks the following:
 *       - if JVMTI_ERROR_NULL_POINTER is returned when threadsCountPtr is null
 *       - if JVMTI_ERROR_NULL_POINTER is returned when threadsPtr is null
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @run main/othervm/native -agentlib:GetAllThreadsNullTest GetAllThreadsNullTest
 */

public class GetAllThreadsNullTest {

    static {
        System.loadLibrary("GetAllThreadsNullTest");
    }

    native static boolean check();

    public static void main(String args[]) {
        if (!check()) {
            throw new RuntimeException("GetAllThreads doesn't fail if one of parameter is NULL.");
        }
    }

}
