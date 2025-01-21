/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.internal.misc;

import sun.io.Win32ErrorMode;

public class OSEnvironment {

    /*
     * Initialize any miscellaneous operating system settings that need to be set
     * for the class libraries.
     * <p>
     * At this time only the process-wide error mode needs to be set.
     */
    public static void initialize() {
        Win32ErrorMode.initialize();
    }

}
