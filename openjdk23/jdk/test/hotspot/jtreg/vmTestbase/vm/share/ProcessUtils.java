/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package vm.share;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;

import nsk.share.TestBug;

import com.sun.management.HotSpotDiagnosticMXBean;

public final class ProcessUtils {
    static {
        System.loadLibrary("ProcessUtils");
    }

    private ProcessUtils() {}

    /**
     * Send Ctrl-\ to java process and Ctrl-Break on Windows.
     * This will usually trigger stack dump for all threads and
     * may trigger heap dump.
     *
     * @return true if it was successful
     */
    public static native boolean sendCtrlBreak();
}
