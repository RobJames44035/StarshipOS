/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package jdk.test.lib.jvmti;

import java.io.Serial;

/**
 * Base class for debuggee class in JVMTI tests.
 *
 * <p>This class provides method checkStatus(int) which is used for
 * synchronization between agent and debuggee class.</p>
 *
 * @see #checkStatus(int)
 */
public class DebugeeClass {

    public static final int TEST_PASSED = 0;
    public static final int TEST_FAILED = 2;

    /**
     * This method is used for synchronization status between agent and debuggee class.
     */
    public synchronized static native int checkStatus(int status);

    /**
     * Reset agent data to prepare for another run.
     */
    public synchronized static native void resetAgentData();

    /**
     * This method is used to load library with native methods implementation, if needed.
     */
    @SuppressWarnings("restricted")
    public static void loadLibrary(String name) {
        try {
            System.loadLibrary(name);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("# ERROR: Could not load native library: " + name);
            System.err.println("# java.library.path: "
                                + System.getProperty("java.library.path"));
            throw e;
        }
    }

    public static void safeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public class Failure extends RuntimeException {
        @Serial
        private static final long serialVersionUID = -4069390356498980839L;

        public Failure() {
        }

        public Failure(Throwable cause) {
            super(cause);
        }
    }
}
