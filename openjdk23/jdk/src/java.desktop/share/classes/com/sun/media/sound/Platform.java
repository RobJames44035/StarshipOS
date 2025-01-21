/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package com.sun.media.sound;

import java.util.StringTokenizer;

/**
 * Audio configuration class for exposing attributes specific to the platform or system.
 *
 * @author Kara Kytle
 * @author Florian Bomers
 */
final class Platform {

    // native library we need to load
    private static final String libName = "jsound";

    private static boolean isNativeLibLoaded;

    // SYSTEM CHARACTERISTICS
    // vary according to hardware architecture

    // intel is little-endian.  sparc is big-endian.
    private static boolean bigEndian;

    static {
        loadLibraries();
    }

    /**
     * Private constructor.
     */
    private Platform() {
    }

    /**
     * Dummy method for forcing initialization.
     */
    static void initialize() {
    }

    /**
     * Determine whether the system is big-endian.
     */
    static boolean isBigEndian() {
        return bigEndian;
    }

    /**
     * Load the native library or libraries.
     */
    @SuppressWarnings("restricted")
    private static void loadLibraries() {
        // load the native library
        isNativeLibLoaded = true;
        try {
            System.loadLibrary(libName);
        } catch (Throwable t) {
            if (Printer.err) Printer.err("Couldn't load library "+libName+": "+t.toString());
            isNativeLibLoaded = false;
        }
        if (isNativeLibLoaded) {
            bigEndian = nIsBigEndian();
        }
    }

    static boolean isMidiIOEnabled() {
        return isNativeLibLoaded;
    }

    static boolean isPortsEnabled() {
        return isNativeLibLoaded;
    }

    static boolean isDirectAudioEnabled() {
        return isNativeLibLoaded;
    }

    // the following native method is implemented in Platform.c
    private static native boolean nIsBigEndian();
}
