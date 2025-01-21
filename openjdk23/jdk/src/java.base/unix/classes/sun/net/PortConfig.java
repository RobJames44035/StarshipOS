/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package sun.net;

import jdk.internal.util.OperatingSystem;

/**
 * Determines the ephemeral port range in use on this system.
 * If this cannot be determined, then the default settings
 * of the OS are returned.
 */

public final class PortConfig {

    private static final int upper, lower;

    private PortConfig() {}

    static {
        jdk.internal.loader.BootLoader.loadLibrary("net");
        int defaultUpper;
        int defaultLower;
        switch (OperatingSystem.current()) {
            case LINUX:
                defaultLower = 32768;
                defaultUpper = 61000;
                break;
            case MACOS:
                defaultLower = 49152;
                defaultUpper = 65535;
                break;
            case AIX:
                // The ephemeral port is OS version dependent on AIX:
                // http://publib.boulder.ibm.com/infocenter/aix/v7r1/topic/com.ibm.aix.rsct315.admin/bl503_ephport.htm
                // However, on AIX 5.3 / 6.1 / 7.1 we always see the
                // settings below by using:
                // /usr/sbin/no -a | fgrep ephemeral
                defaultLower = 32768;
                defaultUpper = 65535;
                break;
            default:
                throw new InternalError("sun.net.PortConfig: unsupported OS: " + OperatingSystem.current());
        }

        int v = getLower0();
        if (v == -1) {
            v = defaultLower;
        }
        lower = v;

        v = getUpper0();
        if (v == -1) {
            v = defaultUpper;
        }
        upper = v;
    }

    static native int getLower0();
    static native int getUpper0();

    public static int getLower() {
        return lower;
    }

    public static int getUpper() {
        return upper;
    }
}
