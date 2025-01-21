/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package sun.awt;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import sun.awt.windows.WToolkit;

public class PlatformGraphicsInfo {

    private static final boolean hasDisplays;

    static {
        loadAWTLibrary();
        hasDisplays = hasDisplays0();
    }

    @SuppressWarnings("restricted")
    private static void loadAWTLibrary() {
        System.loadLibrary("awt");
    }

    private static native boolean hasDisplays0();

    public static GraphicsEnvironment createGE() {
        return new Win32GraphicsEnvironment();
    }

    public static Toolkit createToolkit() {
        return new WToolkit();
    }

    public static boolean getDefaultHeadlessProperty() {
        // If we don't find usable displays, we run headless.
        return !hasDisplays;
    }

    /*
     * Called from java.awt.GraphicsEnvironment when
     * getDefaultHeadlessProperty() has returned true, and
     * the application has called an API that requires headful.
     */
    public static String getDefaultHeadlessMessage() {
        return
            "\nThe application does not have desktop access,\n" +
            "but this program performed an operation which requires it.";
    }
}
