/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.io.*;


/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/NativeMethodBind/nativemethbind002.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     This test exercises the JVMTI event NativeMethodBind.
 *     It verifies that the events will be sent only during the start
 *     and live phase of VM execution.
 *     The test works as follows. The NativeMethodBind event is enabled
 *     on 'OnLoad' phase. Then the VM phase is checked from the
 *     NativeMethodBind callback to be start or live one. The java part
 *     calls the dummy native method 'nativeMethod' on exit in order to
 *     provoke the NativeMethodBind event near the dead phase.
 * COMMENTS
 *     Fixed the 4995867 bug.
 *
 * @library /test/lib
 * @run main/othervm/native -agentlib:nativemethbind02 nativemethbind02
 */

public class nativemethbind02 {
    static {
        System.loadLibrary("nativemethbind02");
    }

    native int nativeMethod();

    public static void main(String[] argv) {
        new nativemethbind02().runThis();
    }

    private void runThis() {

        System.out.println("\nCalling a native method ...\n");

        // dummy methods used to provoke the NativeMethodBind event
        // near the dead phase
        int result = nativeMethod();
        if (result != 0) {
            throw new RuntimeException("runThis() returned " + result);
        }
    }
}
