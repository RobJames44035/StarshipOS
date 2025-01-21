/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.net.http.common;

/**
 * An alternative timeline that can deliver deadline instants.
 */
public interface TimeLine {
    /**
     * {@return the current instant on this alternative timeline}
     */
    Deadline instant();
}
