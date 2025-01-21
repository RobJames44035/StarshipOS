/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.share.jvmti;

import nsk.share.*;
import nsk.share.jvmti.*;

/**
 * Base class for debuggee class in JVMTI tests.
 *
 * <p>This class provides method checkStatus(int) which is used for
 * synchronization between agent and debuggee class.</p>
 *
 * @see #checkStatus(int)
 */
public class DebugeeClass {
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
            throw new Failure(e.getMessage());
        }
    }

}
