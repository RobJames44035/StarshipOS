/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */


import java.io.*;

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/Breakpoint/breakpoint001.
 * VM Testbase keywords: [quick, jpda, jvmti, onload_only_caps, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     This test exercises the JVMTI event Breakpoint.
 *     It verifies that thread info, method info and location of received
 *     Breakpoint events will be the same with two breakpoints previously
 *     set on the methods 'bpMethod()' and 'bpMethod2()' via the function
 *     SetBreakpoint().
 * COMMENTS
 *
 * @library /test/lib
 *
 * @comment make sure breakpoint01 is compiled with full debug info
 * @clean breakpoint01
 * @compile -g:lines,source,vars breakpoint01.java
 * @run main/othervm/native -agentlib:breakpoint01 breakpoint01
 */


/**
 * This test exercises the JVMTI event <code>Breakpoint</code>.
 * <br>It verifies that thread info, method info and location of
 * received Breakpoint events will be the same with two breakpoints
 * previously set on the methods <code>bpMethod()</code> and
 * <code>bpMethod2()</code> via the function SetBreakpoint().
 */
public class breakpoint01 {
    static {
        System.loadLibrary("breakpoint01");
    }

    native int check();

    public static void main(String[] argv) {
        int result = new breakpoint01().runThis();
        if (result != 0 ) {
            throw new RuntimeException("Check returned " + result);
        }
    }

    private int runThis() {
        Runnable virtualThreadTest = () -> {
            Thread.currentThread().setName("breakpoint01Thr");
            System.out.println("Reaching a breakpoint method ...");
            bpMethodV();
            System.out.println("The breakpoint method leaved ...");
        };

        Thread thread = Thread.startVirtualThread(virtualThreadTest);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread.currentThread().setName("breakpoint01Thr");
        bpMethod();
        return check();
    }

    /**
     * dummy method used only to reach breakpoint set in the agent
     */
    private void bpMethod() {
        int dummyVar = bpMethod2();
    }

    /**
     * dummy method used only to reach breakpoint set in the agent
     */
    private int bpMethod2() {
        return 0;
    }

    /**
     * dummy method used only to reach breakpoint set in the agent
     */
    private void bpMethodV() {
        int dummyVar = bpMethod2V();
    }

    /**
     * dummy method used only to reach breakpoint set in the agent
     */
    private int bpMethod2V() {
        return 0;
    }
}
