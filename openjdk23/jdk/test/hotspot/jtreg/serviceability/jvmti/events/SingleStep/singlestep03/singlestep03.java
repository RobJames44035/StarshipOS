/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.io.*;

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/SingleStep/singlestep003.
 * VM Testbase keywords: [quick, jpda, jvmti, onload_only_caps, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     This test exercises the JVMTI event SingleStep.
 *     It verifies that no single step event will be generated from
 *     within native methods.
 *     The test works as follows. Breakpoint is set at special method
 *     'bpMethod()'. Upon reaching the breakpoint, agent enables
 *     SingleStep event generation and checks the events. The java part
 *     calls native method 'nativeMethod()' which calls another native
 *     'anotherNativeMethod()' in order to provoke the SingleStep events
 *     from within native methods. When 'bpMethod()' is leaved and
 *     accordingly, the program returns to the calling method 'runThis()',
 *     the agent disables the event generation.
 * COMMENTS
 *
 * @library /test/lib
 * @compile singlestep03.java
 * @run main/othervm/native -agentlib:singlestep03 singlestep03 platform
 * @run main/othervm/native -agentlib:singlestep03 singlestep03 virtual
 */

public class singlestep03 {
    static {
        System.loadLibrary("singlestep03");
    }

    static volatile int result;
    native void nativeMethod();
    native void anotherNativeMethod(int i);

    native int check();

    public static void main(String[] args) throws Exception {
        Thread.Builder builder;
        if ("virtual".equals(args[0])) {
            builder = Thread.ofVirtual();
        } else {
            builder = Thread.ofPlatform();
        }
        Thread thread = builder.start(() -> {
            result = new singlestep03().runThis();
        });
        thread.join();
        if (result != 0) {
            throw new RuntimeException("Unexpected status: " + result);
        }
    }

    private int runThis() {

        System.out.println("\nReaching a breakpoint method ...\n");
        bpMethod();
        System.out.println("The breakpoint method leaved ...");

        return check();
    }

    /**
     * dummy method used to reach breakpoint, enable the SingleStep
     * event in the agent and provoke SingleStep for native method
     */
    private void bpMethod() {
        nativeMethod();
    }
}
