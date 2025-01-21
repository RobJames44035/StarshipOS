/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/ThreadStart/threadstart003.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     Regression test for bug
 *         4266590 Thread start events are sent from the wrong thread
 *         Release summary: kestrel
 *         Hardware version: generic
 *         O/S version (unbundled products): generic
 * COMMENTS
 *     The test reproduced the bug on winNT 1.3.0-E build.
 *     Ported from JVMDI.
 *
 * @library /test/lib
 * @run main/othervm/native -agentlib:threadstart03 threadstart03
 */


public class threadstart03 {

    final static String ThreadName = "testedThread";

    static {
        System.loadLibrary("threadstart03");
    }

    native static int check(Thread thr, String name);

    public static void main(String args[]) {
        int result = check(new Thread(ThreadName), ThreadName);
        if (result != 0) {
            throw new RuntimeException("Unexpected status: " + result);
        }
    }

}
