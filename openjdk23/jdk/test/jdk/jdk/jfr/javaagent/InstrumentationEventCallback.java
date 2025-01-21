/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package jdk.jfr.javaagent;

import java.util.concurrent.atomic.AtomicBoolean;

public class InstrumentationEventCallback {
    private static AtomicBoolean wasCalled = new AtomicBoolean(false);

    public static void callback() {
        wasCalled.set(true);
    }

    public static void clear() {
        wasCalled.set(false);
    }

    public static boolean wasCalled() {
        return wasCalled.get();
    }
}

