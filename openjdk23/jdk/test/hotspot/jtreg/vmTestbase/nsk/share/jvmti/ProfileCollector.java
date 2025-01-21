/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.share.jvmti;

/**
 * Profile collector class intended to be used with HotSwap-agent.
 *
 * <p>This class provides methods which are used in bytecode instrumentation
 * to profile method calls and memory allocations.</p>
 */

public class ProfileCollector {

    static int callCount = 0;
    static int allocCount = 0;

    public static void reset() {
        callCount = 0;
        allocCount = 0;
    }

    public static void callTracker() {
        ++callCount;
    }

    public static void allocTracker() {
        ++allocCount;
    }
}
