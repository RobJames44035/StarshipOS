/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/GetThreadState/thrstat004.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI function
 *       GetThreadState(thread, threadStatusPtr)
 *     The test checks if the function returns:
 *       - JVMTI_ERROR_NULL_POINTER if threadStatusPtr is null
 *       - JVMTI_ERROR_NULL_POINTER if suspendStatusPtr is null
 *       - JVMTI_ERROR_INVALID_THREAD if thread is not a thread object
 * COMMENTS
 *     Converted the test to use GetThreadState instead of GetThreadStatus.
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @run main/othervm/native -agentlib:thrstat04 thrstat04
 */

public class thrstat04 {

    native static int check(Thread thr);

    public static void main(String args[]) {
        int result = check(Thread.currentThread());
        if (result != 0) {
            throw new RuntimeException("check failed with result " + result);
        }
    }
}
