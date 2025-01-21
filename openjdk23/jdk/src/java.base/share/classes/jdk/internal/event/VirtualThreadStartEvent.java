/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.internal.event;

/**
 * Event recording that a virtual thread has been started.
 */
public class VirtualThreadStartEvent extends Event {
    private static final VirtualThreadStartEvent EVENT = new VirtualThreadStartEvent();

    /**
     * Returns {@code true} if event is enabled, {@code false} otherwise.
     */
    public static boolean isTurnedOn() {
        return EVENT.isEnabled();
    }

    public long javaThreadId;
}
