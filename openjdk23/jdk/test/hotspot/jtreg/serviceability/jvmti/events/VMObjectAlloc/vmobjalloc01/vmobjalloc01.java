/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import jdk.test.lib.jvmti.DebugeeClass;

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/VMObjectAlloc/vmobjalloc001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI event callback function VMObjectAlloc.
 *     The test enables the event and counts a number of received events.
 *     There is no guarantee that VM allocates any special objects, so if
 *     no JVMTI_EVENT_VM_OBJECT_ALLOC has been received then the test
 *     just prints warning message and passes anyway.
 * COMMENTS
 *     Fixed the 5001806 bug.
 *     Modified due to fix of the bug
 *     5010571 TEST_BUG: jvmti tests with VMObjectAlloc callbacks should
 *             be adjusted to new spec
 *
 * @library /test/lib
 * @run main/othervm/native -agentlib:vmobjalloc01 vmobjalloc01
 */

public class vmobjalloc01 extends DebugeeClass {

    public static void main(String argv[]) {
        new vmobjalloc01().runIt();
    }

    int status = TEST_PASSED;

    // run debuggee
    public void runIt() {

        System.out.println("Sync: debuggee started");
        int result = checkStatus(status);
        if (result != 0) {
            throw new RuntimeException("checkStatus() returned " + result);
        }
        System.out.println("TEST PASSED");
    }
}
