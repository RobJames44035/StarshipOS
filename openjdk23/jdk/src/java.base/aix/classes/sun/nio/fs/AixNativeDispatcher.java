/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package sun.nio.fs;

/**
 * AIX specific system calls.
 */

class AixNativeDispatcher extends UnixNativeDispatcher {
    private AixNativeDispatcher() { }

    /**
     * Special implementation of 'getextmntent' (see SolarisNativeDispatcher)
     * that returns all entries at once.
     */
    static native UnixMountEntry[] getmntctl() throws UnixException;

    // initialize
    private static native void init();

    static {
        jdk.internal.loader.BootLoader.loadLibrary("nio");
        init();
    }
}
