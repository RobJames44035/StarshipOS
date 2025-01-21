/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.io.*;

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/NativeMethodBind/nativemethbind001.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     This test exercises the JVMTI event NativeMethodBind.
 *     It verifies that the event will be properly sent:
 *         - for the native method called for the first time
 *         - when the JNI RegisterNatives() is called.
 *     The test works as follows. The java part invokes the native method
 *     'nativeMethod()' twice. At the first time that method registers
 *     another native method 'anotherNativeMethod()' for the dummy class
 *     'TestedClass'. Registration is made through the JNI RegisterNatives()
 *     call. Being invoked at the second time, the nativeMethod() just returns.
 *     In accordance with the spec, it is expected that the NativeMethodBind
 *     will be generated only one time for the nativeMethod(), and only one
 *     time for the anotherNativeMethod().
 * COMMENTS
 *     The test has been fixed due to the bug 4967116.
 *     Fixed the 4995867 bug.
 *
 * @library /test/lib
 * @run main/othervm/native -agentlib:nativemethbind01 nativemethbind01
 */

public class nativemethbind01 {
    static {
        System.loadLibrary("nativemethbind01");
    }

    native void nativeMethod(boolean registerNative);

    native int check();

    public static void main(String[] argv) {
        new nativemethbind01().runThis();
    }

    private void runThis() {
        System.out.println("\nCalling native methods ...\n");

        // dummy method used to provoke the NativeMethodBind event
        nativeMethod(true);

        // call one more time to provoke the wrong NativeMethodBind
        // event
        nativeMethod(false);

        int result = check();
        if (result != 0) {
            throw new RuntimeException("runThis() returned " + result);
        }
    }

   /**
    * Dummy class used only to register native method
    * <code>anotherNativeMethod</code> with it
    */
    class TestedClass {
        native void anotherNativeMethod();
    }
}
